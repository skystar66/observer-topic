package com.topic.provider.topic;

import java.util.Arrays;

/**
 * @author: leon (pengli@58coin.com)
 * @createDate: 2018/4/5
 * @company: (C) Copyright 58BTC 2018
 * @since: JDK 1.8
 * @description:
 */
public enum TopicType {

    P2P(1),
    FANOUT(2),

    ;

    private int code;

    TopicType(int code){
        this.code = code;
    }

    public int getCode(){
        return code;
    }

    public static TopicType of(int code){
        return Arrays.stream(TopicType.values())
                .filter(topicType -> topicType.getCode() == code)
                .findFirst()
                .orElse(null);
    }

}
