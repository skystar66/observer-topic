package com.topic.customer.client;

import com.topic.customer.config.TopicClientConfig;
import com.topic.netty.client.init.RpcClientInitializer;
import com.topic.netty.dto.ManagerProperties;
import com.topic.netty.server.init.RpcServerInitializer;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

public class TopicClient implements Runnable {


    TopicClientConfig rpcConfig;

    RpcClientInitializer rpcClientInitializer;


    public TopicClient(TopicClientConfig rpcConfig,
                       RpcClientInitializer rpcClientInitializer) {
        this.rpcConfig = rpcConfig;
        this.rpcClientInitializer = rpcClientInitializer;
    }

    @Override
    public void run() {
        // 2. 初始化RPC Server
        ManagerProperties managerProperties = new ManagerProperties();
        managerProperties.setRpcPort(rpcConfig.getPort());
        managerProperties.setRpcHost(rpcConfig.getHost());
        rpcClientInitializer.init(Arrays.asList(managerProperties), true);
    }
}
