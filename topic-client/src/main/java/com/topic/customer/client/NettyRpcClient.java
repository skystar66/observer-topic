package com.topic.customer.client;

import com.topic.customer.client.channel.NettyChannelManager;
import com.topic.customer.enums.RpcResponseState;
import com.topic.customer.loadbalance.RpcLoadBalance;
import com.topic.msg.dto.MessageDto;
import com.topic.msg.dto.RpcCmd;
import com.topic.netty.content.RpcCmdContext;
import com.topic.netty.content.RpcContent;
import com.topic.netty.util.SnowflakeIdWorker;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Description: netty rpc  client  通讯实现类
 * Company: wanbaApi
 * Date: 2018/12/10
 *
 * @author xuliang
 */
@Component
@Slf4j
public class NettyRpcClient extends RpcClient {

    @Autowired
    RpcLoadBalance rpcLoadBalancel;


    @Override
    public MessageDto request(RpcCmd rpcCmd) throws Exception {
        return request(rpcCmd, 2000);
    }

    @Override
    public MessageDto request(RpcCmd rpcCmd, long timeout) throws Exception {
        long startTime = System.currentTimeMillis();
        String key = String.valueOf(SnowflakeIdWorker.getInstance().nextId());
        RpcContent rpcContent = RpcCmdContext.getInstance().addKey(key);
        rpcCmd.setKey(key);
        MessageDto result = request0(rpcContent, rpcCmd, timeout);
        log.info("cmd request used time: {} ms", System.currentTimeMillis() - startTime);
        return result;
    }

    private MessageDto request0(RpcContent rpcContent, RpcCmd rpcCmd, long timeout) throws Exception {
        log.info("get channel, key:{}", rpcCmd.getKey());
        Channel channel = rpcLoadBalancel.getRemoteChannel();
        channel.writeAndFlush(rpcCmd);
        log.info("await response key : {}", rpcCmd.getKey());
        //阻塞结果
        if (timeout < 0) {
            //一直阻塞
            rpcContent.await();
        } else {
            rpcContent.await(timeout);
        }
        MessageDto messageDto = rpcContent.getRes();
        return messageDto;
    }

}
