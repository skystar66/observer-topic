package com.topic.provider.topic;


import com.topic.msg.dto.MessageBO;
import com.topic.msg.dto.MessageDto;
import com.topic.msg.dto.RpcCmd;
import com.topic.provider.topic.observer.Topic;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DirectTopic extends AbstractTopic implements Topic {


    private boolean durable;

    public DirectTopic(int id, TopicType type, String topicName, boolean durable) {
        super(id, type, topicName);
        this.durable = durable;
    }

    @Override
    public boolean isDurable() {
        return durable;
    }

    @Override
    public void notify(MessageBO messageBO) {
        getSubscriberMap().values().forEach(subscriber -> {
            RpcCmd rpcCmd = new RpcCmd();
            MessageDto messageDto = new MessageDto();
            messageDto.setData(messageBO);
            rpcCmd.setMsg(messageDto);
            subscriber.send(rpcCmd);
        });
    }


}
