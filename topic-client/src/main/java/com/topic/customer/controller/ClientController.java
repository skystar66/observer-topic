package com.topic.customer.controller;


import com.alibaba.fastjson.JSONObject;
import com.topic.customer.client.RpcClient;
import com.topic.customer.client.channel.NettyChannelManager;
import com.topic.customer.loadbalance.RpcLoadBalance;
import com.topic.msg.MessageConstants;
import com.topic.msg.dto.MessageDto;
import com.topic.msg.dto.RpcCmd;
import com.topic.msg.enums.EventType;
import com.topic.netty.util.SnowflakeIdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/client")
public class ClientController {


    @Autowired
    RpcClient rpcClient;


    /**
     * 登录
     */

    @RequestMapping("/login")
    public String login() throws Exception {
        RpcCmd rpcCmd = new RpcCmd();
        rpcCmd.setKey(EventType.LOGIN.name() +
                SnowflakeIdWorker.getInstance().nextId());
        MessageDto messageDto = new MessageDto();
        messageDto.setData("xuliang");
        messageDto.setState(MessageConstants.STATE_OK);
        rpcCmd.setMsg(messageDto);
        rpcCmd.setEvent(EventType.LOGIN.name());
        MessageDto messageDto1 = rpcClient.request(rpcCmd);

        return JSONObject.toJSONString(messageDto1);
    }


    /**
     * 登出
     */
    @RequestMapping("/logout")
    public String logout() throws Exception {
        RpcCmd rpcCmd = new RpcCmd();
        rpcCmd.setKey(EventType.LOGOUT.name() +
                SnowflakeIdWorker.getInstance().nextId());
        MessageDto messageDto = new MessageDto();
        messageDto.setData("xuliang");
        messageDto.setState(MessageConstants.STATE_OK);
        rpcCmd.setMsg(messageDto);
        rpcCmd.setEvent(EventType.LOGOUT.name());
        MessageDto messageDto1 = rpcClient.request(rpcCmd);

        return JSONObject.toJSONString(messageDto1);
    }


    /**
     * 订阅
     */
    @RequestMapping("/sub")
    public String sub() throws Exception {
        RpcCmd rpcCmd = new RpcCmd();
        rpcCmd.setKey(EventType.SUB.name() +
                SnowflakeIdWorker.getInstance().nextId());
        MessageDto messageDto = new MessageDto();
        messageDto.setData("xuliang");
        messageDto.setState(MessageConstants.STATE_OK);
        rpcCmd.setMsg(messageDto);
        rpcCmd.setEvent(EventType.SUB.name());
        MessageDto messageDto1 = rpcClient.request(rpcCmd);

        return JSONObject.toJSONString(messageDto1);
    }

    /**
     * 取消订阅
     */
    @RequestMapping("/cancel")
    public String cancel() throws Exception {
        RpcCmd rpcCmd = new RpcCmd();
        rpcCmd.setKey(EventType.CANCEL.name() +
                SnowflakeIdWorker.getInstance().nextId());
        MessageDto messageDto = new MessageDto();
        messageDto.setData("xuliang");
        messageDto.setState(MessageConstants.STATE_OK);
        rpcCmd.setMsg(messageDto);
        rpcCmd.setEvent(EventType.CANCEL.name());
        MessageDto messageDto1 = rpcClient.request(rpcCmd);
        return JSONObject.toJSONString(messageDto1);
    }


}
