package com.topic.msg.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * @author xuliang
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class MessageDto implements Serializable {


    /**
     * 请求动作
     */
    private String cmd;


    /**
     * 请求参数
     */
    private Serializable data;


    public <T> T loadBean(Class<T> tClass) {
        return (T) data;
    }


}
