package com.blue.chat.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author wang
 * @since 2021-12-24
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_message")
public class MessageDo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 消息id
     */
    private String msgId;

    /**
     * 消息内容
     */
    private String msgContent;

    /**
     * 发送者id
     */
    private String senderWechat;

    /**
     * 接收者id
     */
    private String receiverWechat;

    /**
     * 发送时间
     */
    private Date sendTime;

    /**
     * 消息类型 01为普通消息 02为好友请求消息
     */
    private String msgType;

    /**
     * 发送时间
     */
    private Date createTime;

    /**
     * 签收状态  01签收 02未签收
     */
    private String sign;

}
