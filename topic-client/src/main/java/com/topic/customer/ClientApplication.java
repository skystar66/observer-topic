package com.topic.customer;

import com.topic.customer.client.TopicClient;
import com.topic.customer.config.TopicClientConfig;
import com.topic.netty.client.init.RpcClientInitializer;
import com.topic.netty.server.init.RpcServerInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.topic.*"})
public class ClientApplication implements ApplicationRunner {

    @Autowired
    TopicClientConfig rpcConfig;

    @Autowired
    RpcClientInitializer rpcClientInitializer;


    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        Thread thread = new Thread(new TopicClient(rpcConfig, rpcClientInitializer));
        thread.setDaemon(true);
        thread.start();
    }


}
