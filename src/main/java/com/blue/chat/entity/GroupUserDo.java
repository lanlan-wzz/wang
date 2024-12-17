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
@TableName("tb_group_user")
public class GroupUserDo implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 群聊id
     */
    private String groupId;
    /**
     * groupUserId
     */
    private String groupUserId;

    /**
     * 用户wechat
     */
    private String wechat;

    /**
     * 加入时间
     */
    private Date createTime;
}
