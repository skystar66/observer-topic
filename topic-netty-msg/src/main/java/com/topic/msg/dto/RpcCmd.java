package com.topic.msg.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class RpcCmd implements Serializable{

    /**
     * 请求唯一标识
     */

    private String key;

    /**
     * 远程标识关键字 netty 默认为 指定host
     * 默认分配 64457 个端口，形式为：ip:ranDomProt
     * todo remoteKey ??
     */
    private String remoteKey;


    /**
     * eventEnum
     */
    private String event;

    /**
     * 请求的消息内容体
     */
    private MessageDto msg;

}
