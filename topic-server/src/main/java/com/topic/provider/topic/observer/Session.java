package com.topic.provider.topic.observer;

import com.topic.msg.dto.MessageDto;
import com.topic.msg.dto.RpcCmd;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;


/**
 * 观察者实现类《处理用户，多个设备的connection》
 */
public class Session extends SubscriberAdapter implements Subscriber {


    private final String userId;


    public String getId() {
        return userId;
    }

    public Session(String userId) {
        this.userId = userId;
    }

    /**
     * 用来管理多个设备的连接
     */
    private Set<Connection> connections = new CopyOnWriteArraySet<>();


    public int getConnections() {
        return connections.size();
    }

    public void addConnection(Connection connection) {
        connections.add(connection);
    }

    public void removeConnection(Connection connection) {
        connections.remove(connection);
    }


    /**
     * 广播到不同设备
     */
    @Override
    public void send(RpcCmd rpcCmd) {
        connections.forEach(connection -> {
            connection.send(rpcCmd);
        });
    }
}
