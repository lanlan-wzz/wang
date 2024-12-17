package com.blue.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description TODO
 * @Author wzz
 * @Date 2021/12/25 16:51
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMsg {

    /**
     * 消息内容
     */
    private  String messageContent;
    /**
     * 类型
     */
    private  String messageType;
    /**
     * 消息接收者
     */
    private String toMessageId;
}
