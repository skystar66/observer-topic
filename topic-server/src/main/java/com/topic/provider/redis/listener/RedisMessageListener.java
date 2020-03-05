package com.topic.provider.redis.listener;


import com.topic.msg.dto.MessageBO;
import com.topic.provider.redis.handler.RedisMessageHandler;
import com.topic.provider.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RedisMessageListener {


    @Autowired
    private RedisMessageHandler messageHandler;

    public void notify(String data) {
        log.info("redis subscriber data:{}", data);
        MessageBO messageBO = JsonUtils.parseString(data, MessageBO.class);
        if (messageBO == null) {
            log.error("message convert error: data={}", data);
            return;
        }
        messageHandler.publish(messageBO);
    }


}
