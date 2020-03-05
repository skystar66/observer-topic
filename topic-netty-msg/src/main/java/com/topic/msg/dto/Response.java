package com.topic.msg.dto;


import com.topic.msg.enums.ResponseCode;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.BeanUtils;

/**
 * @author: leon (pengli@58coin.com)
 * @createDate: 2018/3/28
 * @company: (C) Copyright 58BTC 2018
 * @since: JDK 1.8
 * @description:
 */
@Data
public class Response extends RpcCmd{

    /**
     * 返回码
     */
    private String code;

    /**
     * 消息
     */
    private String msg;

    /**
     * @param request
     * @return
     */
    private Object data;

    public static Response success(RpcCmd request) {
        Response response = new Response();

        response.setData(request);
        response.setCode("0");
        return response;
    }

    public static Response success(RpcCmd request, Object data) {
        Response response = new Response();
        response.setData(data);
        response.setCode("0");
        return response;
    }

    public static Response error(RpcCmd request, ResponseCode errorCode) {
        Response response = new Response();
        response.setCode(errorCode.getCode());
        response.setMsg(errorCode.getDesc());
        response.setData(request);
        return response;
    }


    @Override
    public String toString() {
        return "Response{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
