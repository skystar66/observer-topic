package com.topic.provider.server;


import com.topic.msg.MessageConstants;
import com.topic.msg.dto.MessageDto;
import com.topic.msg.dto.RpcCmd;
import com.topic.msg.enums.ResponseCode;

import java.io.Serializable;

/**
 * 消息创建器
 *
 * @author xuliang
 */
public class MessageCreator {


    /**
     * 正常响应
     *
     * @param action  action
     * @param message message
     * @return MessageDto
     */
    public static RpcCmd okResponse(Serializable message, String action) {
        RpcCmd rpcCmd = new RpcCmd();
        MessageDto messageDto = new MessageDto();
        messageDto.setState(MessageConstants.STATE_OK);
        messageDto.setCmd(action);
        messageDto.setData(message);
        rpcCmd.setMsg(messageDto);
        return rpcCmd;
    }

    /**
     * 失败响应
     *
     * @param action  action
     * @param message message
     * @return MessageDto
     */
    public static RpcCmd failResponse(Serializable message, String action) {
        RpcCmd rpcCmd = new RpcCmd();

        MessageDto messageDto = new MessageDto();
        messageDto.setCmd(action);
        messageDto.setState(MessageConstants.STATE_EXCEPTION);
        messageDto.setData(message);
        rpcCmd.setMsg(messageDto);
        return rpcCmd;
    }


    /**
     * 失败响应
     *
     * @param action  action
     * @param message message
     * @return MessageDto
     */
    public static RpcCmd bussinesError(ResponseCode responseCode) {
        RpcCmd rpcCmd = new RpcCmd();
        MessageDto messageDto = new MessageDto();
        messageDto.setState(Integer.parseInt(responseCode.getCode()));
        rpcCmd.setMsg(messageDto);
        return rpcCmd;
    }


    /**
     * 服务器错误
     *
     * @param action action
     * @return MessageDto
     */
    public static MessageDto serverException(String action) {
        MessageDto messageDto = new MessageDto();
        messageDto.setCmd(action);
        messageDto.setState(MessageConstants.STATE_EXCEPTION);
        return messageDto;
    }


}
