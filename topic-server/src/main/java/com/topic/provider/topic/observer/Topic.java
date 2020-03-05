package com.topic.provider.topic.observer;


import com.topic.msg.dto.MessageBO;
import com.topic.provider.topic.TopicType;

/**
 * 被观察者 topic，用来管理订阅者/观察者信息
 * */

public interface Topic {


    int getId();

    String getName();

    TopicType getType();

    boolean isDurable();


    /**
     * 通知 Subscriber
     *
     * @param
     */
    void notify(MessageBO messageBO);

    /**
     * 增加订阅者
     *
     * @param subscriber
     */
    void addSubscriber(Subscriber subscriber);

    /**
     * 根据id查找订阅者
     *
     * @param id
     * @return
     */
    Subscriber getSubscriber(String id);

    /**
     * 移除订阅者
     * @param subscriber
     * @return
     */
    Subscriber removeSubscriber(Subscriber subscriber);








}
