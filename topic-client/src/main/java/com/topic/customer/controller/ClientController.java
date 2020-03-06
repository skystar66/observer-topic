package com.topic.customer.controller;


import com.alibaba.fastjson.JSONObject;
import com.topic.customer.client.RpcClient;
import com.topic.customer.client.channel.NettyChannelManager;
import com.topic.customer.loadbalance.RpcLoadBalance;
import com.topic.customer.utils.JsonUtils;
import com.topic.msg.MessageConstants;
import com.topic.msg.dto.MessageBO;
import com.topic.msg.dto.MessageDto;
import com.topic.msg.dto.RpcCmd;
import com.topic.msg.enums.EventType;
import com.topic.netty.util.SnowflakeIdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.topic.customer.utils.TopicConstants.SUB_MALL;
import static com.topic.customer.utils.TopicConstants.SUB_MSG;
import static com.topic.customer.utils.TopicConstants.SUB_ORDER;

@RestController
@RequestMapping("/client")
public class ClientController {


    @Autowired
    RpcClient rpcClient;


    @Autowired
    StringRedisTemplate stringRedisTemplate;


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

    /**
     * redis pub
     */
    @RequestMapping("/pubTopic")
    public String pubTopic() throws Exception {

        MessageBO messageBO = new MessageBO();
        messageBO.setPayload("订单消息订阅");
        messageBO.setTopicName(SUB_ORDER);

        stringRedisTemplate.convertAndSend(SUB_ORDER, JsonUtils.toJSONString(messageBO));


        MessageBO messageBO1 = new MessageBO();
        messageBO1.setPayload("邮件消息订阅");
        messageBO1.setTopicName(SUB_ORDER);

        stringRedisTemplate.convertAndSend(SUB_MALL, JsonUtils.toJSONString(messageBO1));


        MessageBO messageBO2 = new MessageBO();
        messageBO2.setPayload("聊天消息订阅");
        messageBO2.setTopicName(SUB_ORDER);

        stringRedisTemplate.convertAndSend(SUB_MSG, JsonUtils.toJSONString(messageBO2));
        return "SUCCESS";
    }


}
