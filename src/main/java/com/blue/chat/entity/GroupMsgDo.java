package com.blue.chat.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 
 * @author 26020
 * @TableName tb_group_msg
 */
@TableName(value ="tb_group_msg")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class GroupMsgDo implements Serializable {
    /**
     * 
     */
    @TableId(value = "group_msg_id",type = IdType.AUTO)
    private String groupMsgId;

    /**
     * 群id
     */
    private String groupId;

    /**
     * 消息内容
     */
    private String groupMsg;

    /**
     * 创建时间
     */
    private Date createTime;
}