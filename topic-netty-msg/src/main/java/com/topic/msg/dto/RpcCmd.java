package com.topic.msg.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class RpcCmd {

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
     //     * 参数 不同的类型有不同的参数
     //     * <p>
     //     * 登录  access_token
     //     * 订阅  type    order  advertise
     //     * ACK  messageId
     //     */
//    private Map<String, Object> params;


    /**
     * 请求参数
     */
    private Serializable data;


    public <T> T loadBean(Class<T> tClass) {
        return (T) data;
    }

}
