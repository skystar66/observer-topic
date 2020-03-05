package com.topic.netty.client.handler;


import com.topic.msg.MessageConstants;
import com.topic.msg.dto.MessageDto;
import com.topic.msg.dto.RpcCmd;
import com.topic.netty.util.NettyContext;
import com.topic.netty.util.SnowflakeIdWorker;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.ConnectException;
import java.net.SocketAddress;
import java.util.List;

/**
 * Description:
 * Company: CodingApi
 * Date: 2018/12/21
 *
 * @author xuliang
 * @desc:重试handler
 */
@Slf4j
@ChannelHandler.Sharable
@Component
public class NettyClientRetryHandler extends ChannelInboundHandlerAdapter {



    private RpcCmd heartCmd;

    @Autowired
    NettyRetryConnect retryConnect;

    /**
     * 构建心跳信息
     */
    public NettyClientRetryHandler() {
        MessageDto messageDto = new MessageDto();
        messageDto.setCmd(MessageConstants.ACTION_HEART_CHECK);
        heartCmd = new RpcCmd();
        heartCmd.setMsg(messageDto);
        heartCmd.setKey(MessageConstants.ACTION_HEART_CHECK
                + SnowflakeIdWorker.getInstance().nextId());
        heartCmd.setEvent(MessageConstants.ACTION_HEART_CHECK);

//        this.clientInitCallBack = clientInitCallBack;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
//        keepSize = NettyContext.currentParam(List.class).size();

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        SocketAddress socketAddress = ctx.channel().remoteAddress();
        log.error("socketAddress:{} ", socketAddress);
        retryConnect.reConnect(socketAddress);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("NettyClientRetryHandler - exception . ", cause);

        if (cause instanceof ConnectException) {
            Thread.sleep(1000 * 15);
            log.error("try connect tx-manager:{} ", ctx.channel().remoteAddress());
            retryConnect.reConnect(ctx.channel().remoteAddress());
        }
        //发送数据包检测是否断开连接.
        ctx.writeAndFlush(heartCmd);

    }
}
