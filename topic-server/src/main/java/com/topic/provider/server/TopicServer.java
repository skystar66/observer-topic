package com.topic.provider.server;

import com.topic.netty.dto.ManagerProperties;
import com.topic.netty.server.init.RpcServerInitializer;
import com.topic.provider.config.TopicServerConfig;
import org.springframework.beans.factory.annotation.Autowired;

public class TopicServer implements Runnable {


    @Autowired
    TopicServerConfig rpcConfig;

    @Autowired
    RpcServerInitializer rpcServerInitializer;


    @Override
    public void run() {

//        // 1. 配置
//        if (rpcConfig.getWaitTime() <= 5) {
//            rpcConfig.setWaitTime(1000);
//        }
//        if (rpcConfig.getAttrDelayTime() < 0) {
//            //网络延迟时间 8s
//            rpcConfig.setAttrDelayTime(txManagerConfig.getDtxTime());
//        }

        // 2. 初始化RPC Server
        ManagerProperties managerProperties = new ManagerProperties();
        managerProperties.setCheckTime(rpcConfig.getHeartTime());
        managerProperties.setRpcPort(rpcConfig.getPort());
        managerProperties.setRpcHost(rpcConfig.getHost());
        rpcServerInitializer.init(managerProperties);
    }
}
