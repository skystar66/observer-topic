package com.topic.provider;

import com.topic.netty.server.init.RpcServerInitializer;
import com.topic.provider.config.TopicServerConfig;
import com.topic.provider.server.TopicServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.topic.*"})
public class ProviderApplication implements ApplicationRunner {


    @Autowired
    TopicServerConfig rpcConfig;

    @Autowired
    RpcServerInitializer rpcServerInitializer;


    public static void main(String[] args) {
        SpringApplication.run(ProviderApplication.class, args);
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        Thread thread = new Thread(new TopicServer(rpcConfig, rpcServerInitializer));
        thread.setDaemon(true);
        thread.start();
    }
}

