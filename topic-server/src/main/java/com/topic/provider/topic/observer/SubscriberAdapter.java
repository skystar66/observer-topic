package com.topic.provider.topic.observer;

import java.util.HashSet;
import java.util.Set;

public abstract class SubscriberAdapter implements Subscriber {

    private Set<Topic> topics = new HashSet<>();


    @Override
    public String getId() {
        return null;
    }

    @Override
    public void addTopic(Topic topic) {
        topics.add(topic);
    }

    @Override
    public void clearTopic() {
        topics.clear();
    }

    @Override
    public Set<Topic> getTopics() {
        return topics;
    }

    @Override
    public void removeTopic(Topic topic) {
        topics.remove(topic);
    }
}
