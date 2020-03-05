package com.topic.provider.topic.manager;

import com.topic.msg.dto.Response;
import com.topic.msg.dto.RpcCmd;
import com.topic.provider.server.channel.NettyChannelManager;
import com.topic.provider.topic.DirectTopic;
import com.topic.provider.topic.TopicType;
import com.topic.provider.topic.observer.Connection;
import com.topic.provider.topic.observer.Session;
import com.topic.provider.topic.observer.Topic;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static com.topic.msg.enums.ResponseCode.INVALID_REQUEST;
import static com.topic.msg.enums.ResponseCode.NOT_LOGIN;
import static com.topic.provider.utils.TopicConstants.SUB_MALL;
import static com.topic.provider.utils.TopicConstants.SUB_MSG;
import static com.topic.provider.utils.TopicConstants.SUB_ORDER;

@Component
@Slf4j
public class TopicManager {


    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * channelId, Connection
     */
    private Map<String, Connection> connectionMap = new ConcurrentHashMap<>();

    /**
     * 用户对应的session
     * userId  session
     * <p>
     * userId -- session
     */
    private Map<String, Session> userMap = new ConcurrentHashMap<>();

    /**
     * 系统所有的主题
     * <p>
     * topicName -- Topic
     */
    private Map<String, Topic> topicMap = new ConcurrentHashMap<>();


    /**
     * 获取连接
     */
    public Connection getConnection(Channel channel) {
        return connectionMap.get(channel.id().asLongText());
    }


    /**
     * 创建连接
     */
    public Connection createConnection(Channel channel) {
        Connection connection = new Connection(channel);
        connectionMap.put(connection.getId(), connection);
        return connection;
    }

    /**
     * 用户登录
     */
    public void handleLogin(Connection connection, RpcCmd rpcCmd) {
        Session session = connection.getSession();
        if (session == null) {

            String token = rpcCmd.loadBean(String.class);
            if (StringUtils.isEmpty(token)) {
                connection.send(Response.error(rpcCmd, INVALID_REQUEST));
                return;
            }

            String userId = stringRedisTemplate.opsForValue().get(token);
            if (StringUtils.isEmpty(userId)) {
                connection.send(Response.error(rpcCmd, NOT_LOGIN));
                return;
            }
            session = userMap.computeIfAbsent(userId, Session::new);
            log.info("user first login: userId:{}", session.getId());
            log.info("user first login and channelId is:{}", connection.getId());
        }
        session.addConnection(connection);
        connection.setSession(session);
        log.info("user login: userId:{}", session.getId());
        connection.send(Response.success(rpcCmd));
    }


    /**
     * 用户退出登录
     */
    public void handleLogout(Connection connection, RpcCmd rpcCmd) {
        NettyChannelManager.getInstance().removeChannel(connection.getChannel());
        connectionMap.remove(connection);
        log.info("user logout: channelId:{}", connection.getId());
        Session session = connection.getSession();
        if (session == null) {
            connection.send(Response.error(rpcCmd, NOT_LOGIN));
            return;
        }
        //获取已经订阅的topics
        Set<Topic> topics = session.getTopics();
        //查看当前连接数
        int connectCount = session.getConnections();
        //如果只剩一个连接，那就全部取消订阅
        if (connectCount == 1) {
            topics.forEach(topic -> {
                topic.removeSubscriber(session);
                session.removeTopic(topic);
            });
            userMap.remove(session.getUserId());
            log.info("user logout: userId:{}", session.getId());
        }
        //移除掉当前设备连接
        session.removeConnection(connection);
        connection.send(Response.success(rpcCmd));
    }


    /**
     * 添加订阅
     */
    public void addSubscriber(Connection connection, RpcCmd rpcCmd) {
        Session session = connection.getSession();
        if (session == null) {
            connection.send(Response.error(rpcCmd, NOT_LOGIN));
            return;
        }
        //写死订阅三个topic
        List<String> topicList = Arrays.asList(SUB_ORDER, SUB_MALL, SUB_MSG);

        for (String topicName : topicList) {
            Topic topic = loadTopic(topicName);
            //将topic添加到观察者中topic列表
            session.addTopic(topic);
            //被观察者管理观察者信息，用来通知
            topic.addSubscriber(session);
        }
        connection.send(Response.success(rpcCmd));
    }

    /**
     * 添加订阅
     */
    public void cancelSubscriber(Connection connection, RpcCmd rpcCmd) {
        Session session = connection.getSession();
        if (session == null) {
            connection.send(Response.error(rpcCmd, NOT_LOGIN));
            return;
        }

        //取消该人的所有topic订阅
        topicMap.values().forEach(topic -> {
            topic.removeSubscriber(session);
            session.removeTopic(topic);
        });
        connection.send(Response.success(rpcCmd));
    }


    /**
     * 初始化topic
     */
    public Topic loadTopic(String topicName) {
        if (topicMap.containsKey(topicName)) {
            return topicMap.get(topicName);
        }
        Topic topic = new DirectTopic(stringRedisTemplate.opsForValue().increment("topic-id").intValue(),
                TopicType.P2P, topicName, true);
        topicMap.put(topicName, topic);
        return topic;
    }


}
