package com.topic.provider.topic.observer;

import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;


/**
 * 观察者实现类《处理与客户端的连接发送消息等》
 */
@Slf4j
public class Connection extends SubscriberAdapter implements Subscriber {

    private Channel channel;

    /**
     * 当前连接的session
     */

    private Session session;


    public Session getSession() {
        return session;
    }


    public void setSession(Session session) {
        this.session = session;
    }


    public Channel getChannel() {
        return channel;
    }

    @Override
    public String getId() {
        return channel.id().asLongText();
    }


    public Connection(Channel channel) {
        this.channel = channel;
    }

    @Override
    public void send(Object message) {
        channel.writeAndFlush(message);
    }
}
