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
@TableName("tb_offline_msg")
public class OfflineMsgDo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private String offlineMsgId;

    /**
     * 接收者id
     */
    private String receiverWechat;

    /**
     * 发送者id
     */
    private String senderWechat;

    /**
     * 消息类型
     */
    private String msgType;

    /**
     * 消息内容
     */
    private String msgContent;

    /**
     * 发送时间
     */
    private Date createTime;


}
