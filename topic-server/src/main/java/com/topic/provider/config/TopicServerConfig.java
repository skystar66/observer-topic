package com.topic.provider.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "topic.server")
public class TopicServerConfig {

    /**
     * manager host
     */
    private String host = "127.0.0.1";

    /**
     * support  port
     */
    private int port;

    /**
     * netty heart check time (ms) 20s 心跳检测一次
     */
    private long heartTime = 20 * 1000;


}
