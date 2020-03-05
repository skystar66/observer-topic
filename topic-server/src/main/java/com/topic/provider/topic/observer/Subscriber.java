package com.topic.provider.topic.observer;


import com.topic.msg.dto.MessageDto;
import com.topic.msg.dto.RpcCmd;

import java.util.Set;

/**
 * 订阅者/观察者
 */

public interface Subscriber {

    /**
     * 订阅者/观察者id
     */
    public String getId();


    /**
     * 添加订阅的topic
     */
    void addTopic(Topic topic);

    /**
     * 清理已经订阅的topic
     */
    void clearTopic();

    /**
     * 获取已经订阅的所有topic
     */
    Set<Topic> getTopics();

    /**
     * 移除订阅的topic
     */
    void removeTopic(Topic topic);

    /**
     * 被观察者一旦发生变化 进行通知
     */
    void send(RpcCmd rpcCmd);

}
