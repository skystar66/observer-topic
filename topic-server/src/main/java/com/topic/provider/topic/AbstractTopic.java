package com.topic.provider.topic;

import com.topic.msg.dto.MessageBO;
import com.topic.provider.topic.observer.Subscriber;
import com.topic.provider.topic.observer.Topic;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 负责管理订阅该topic的订阅者
 * */
public abstract class AbstractTopic implements Topic {

    private int id;
    private TopicType type;
    private String topicName;


    /**
     * subscriberId, Subscriber
     */
    private final Map<String, Subscriber> subscriberMap = new ConcurrentHashMap<>();


    public AbstractTopic(int id, TopicType type, String topicName) {
        this.id = id;
        this.type = type;
        this.topicName = topicName;
    }


    @Override
    public Subscriber removeSubscriber(Subscriber subscriber) {

        return subscriberMap.remove(subscriber.getId());
    }

    @Override
    public void addSubscriber(Subscriber subscriber) {
        if (subscriberMap.containsKey(subscriber.getId())) {
            return;
        }
        subscriberMap.put(subscriber.getId(), subscriber);
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return topicName;
    }

    @Override
    public TopicType getType() {
        return type;
    }

    @Override
    public boolean isDurable() {
        return false;
    }

    @Override
    public void notify(MessageBO messageBO) {

    }

    @Override
    public Subscriber getSubscriber(String id) {
        return subscriberMap.get(id);
    }

    public Map<String, Subscriber> getSubscriberMap() {
        return subscriberMap;
    }
}
