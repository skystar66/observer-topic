package com.topic.customer.manager;

import com.topic.customer.controller.channel.NettyChannelManager;
import com.topic.msg.manager.SocketChannelManager;
import io.netty.channel.Channel;
import org.springframework.stereotype.Component;

@Component
public class ChannelManager implements SocketChannelManager {

    @Override
    public void addChannel(Channel channel) {
        NettyChannelManager.getInstance().addChannel(channel);
    }

    @Override
    public void removeChannel(Channel channel) {
        NettyChannelManager.getInstance().removeChannel(channel);
    }

    @Override
    public void getChannel(String channelId) {
        NettyChannelManager.getInstance().getChannel(channelId);
    }

    @Override
    public boolean contains(String channelId) {
        return NettyChannelManager.getInstance().contains(channelId);
    }
}
