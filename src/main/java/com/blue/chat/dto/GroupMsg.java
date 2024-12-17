package com.blue.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description TODO
 * @Author wzz
 * @Date 2021/12/25 17:07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupMsg {
    /**
     * 群聊id
     */
    private String groupId;

    /**
     * 消息
     */
    private String messageContent;

    /**
     * 消息类型
     */
    private  String messageType;
}
