package com.topic.provider.config;


import com.topic.provider.redis.listener.RedisMessageListener;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import java.util.concurrent.Executor;

import static com.topic.provider.utils.RedisConstants.NOTIFY;
import static com.topic.provider.utils.TopicConstants.SUB_MALL;
import static com.topic.provider.utils.TopicConstants.SUB_MSG;
import static com.topic.provider.utils.TopicConstants.SUB_ORDER;

@Configuration
public class RedisMessageListenerConfig {


    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                                   RedisMessageListener redisMessageListener,
                                                   @Qualifier("taskExecutor") Executor taskExecutor) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setTaskExecutor(taskExecutor);
        container.setConnectionFactory(connectionFactory);

        MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter(redisMessageListener, NOTIFY);
        messageListenerAdapter.afterPropertiesSet();
        container.addMessageListener(messageListenerAdapter, new ChannelTopic(SUB_ORDER));
        container.addMessageListener(messageListenerAdapter, new ChannelTopic(SUB_MALL));
        container.addMessageListener(messageListenerAdapter, new ChannelTopic(SUB_MSG));
        return container;
    }


}
