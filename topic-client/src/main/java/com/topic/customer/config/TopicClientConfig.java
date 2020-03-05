package com.topic.customer.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "topic.client")
@Data
public class TopicClientConfig {

    /**
     * manager host
     */
    private String host = "127.0.0.1";

    /**
     * support  port
     */
    private int port;


}
