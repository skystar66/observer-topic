package com.topic.provider.redis.handler;

import com.topic.msg.dto.MessageBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.topic.provider.utils.TopicConstants.SUB_MALL;
import static com.topic.provider.utils.TopicConstants.SUB_MSG;
import static com.topic.provider.utils.TopicConstants.SUB_ORDER;

@Component
public class RedisMessageHandler {

    @Autowired
    MailHandler mailHandler;


    @Autowired
    MsgHandler msgHandler;


    @Autowired
    OrderHandler orderHandler;


    public void publish(MessageBO messageBO) {

        if (messageBO.getTopicName().equals(SUB_ORDER)) {
            orderHandler.onEvent(messageBO);
        } else if (messageBO.getTopicName().equals(SUB_MALL)) {
            mailHandler.onEvent(messageBO);
        } else if (messageBO.getTopicName().equals(SUB_MSG)) {
            msgHandler.onEvent(messageBO);
        }


    }


}
