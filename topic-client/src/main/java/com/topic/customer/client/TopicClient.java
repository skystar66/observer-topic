package com.topic.customer.client;

import com.topic.customer.config.TopicClientConfig;
import com.topic.netty.dto.ManagerProperties;
import com.topic.netty.server.init.RpcServerInitializer;
import org.springframework.beans.factory.annotation.Autowired;

public class TopicClient implements Runnable {


    @Autowired
    TopicClientConfig rpcConfig;

    @Autowired
    RpcServerInitializer rpcServerInitializer;


    @Override
    public void run() {
        // 2. 初始化RPC Server
        ManagerProperties managerProperties = new ManagerProperties();
        managerProperties.setRpcPort(rpcConfig.getPort());
        managerProperties.setRpcHost(rpcConfig.getHost());
        rpcServerInitializer.init(managerProperties);
    }
}
