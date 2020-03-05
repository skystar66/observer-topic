package com.topic.provider.server;

import com.topic.msg.RpcAnswer;
import com.topic.msg.dto.RpcCmd;
import com.topic.msg.enums.EventType;
import com.topic.msg.enums.ResponseCode;
import com.topic.provider.server.channel.NettyChannelManager;
import com.topic.provider.topic.manager.TopicManager;
import com.topic.provider.topic.observer.Connection;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Slf4j
public class BussinesHandler implements RpcAnswer {

    @Autowired
    TopicManager topicManager;


    @Override
    public void callback(RpcCmd rpcCmd) {
        //获取channel
        Channel channel = NettyChannelManager.getInstance().getChannel(rpcCmd.getRemoteKey());
        if (channel == null) {
            log.info("channel 已失效！");
            return;
        }
        if (Objects.isNull(rpcCmd)) {
            channel.writeAndFlush(ResponseCode.INVALID_EVENT);
            log.info("msg is null");
            return;
        }
        //获取connection
        Connection connection = topicManager.getConnection(channel);
        if (connection == null) {
            //创建connection
            connection = topicManager.createConnection(channel);
        }
        //具体业务处理
        EventType eventType = EventType.of(rpcCmd.getEvent());
        switch (eventType) {
            /**登录*/
            case LOGIN:
                topicManager.handleLogin(connection, rpcCmd);
                break;
            /**订阅*/
            case SUB:
                topicManager.addSubscriber(connection, rpcCmd);
                break;
            /**取消订阅*/
            case CANCEL:
                topicManager.cancelSubscriber(connection, rpcCmd);
                break;
            /**退出登录*/
            case LOGOUT:
                topicManager.handleLogout(connection, rpcCmd);
                break;
            default:
                break;
        }
    }
}
