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
 * <p>
 * 
 * </p>
 *
 * @author wang
 * @since 2021-12-19
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_friend")
public class FriendDo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "friend_id", type = IdType.AUTO)
    private String friendId;
    /**
     * 用户id
     */
    private String userWechat;

    /**
     * 朋友id
     */
    private String friendWechat;

    /**
     * 朋友备注
     */
    private String remarks;

    /***
     * 朋友头像
     */
    private String  avatar;

    /**
     * 朋友状态
     */
    private String friendStatus;
    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

}
