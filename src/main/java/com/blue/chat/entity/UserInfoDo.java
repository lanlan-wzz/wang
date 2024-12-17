package com.blue.chat.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
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
 * @since 2021-12-18
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_user_info")
public class UserInfoDo implements Serializable {

    private static final long serialVersionUID = 1L;


    private String userInfoId;
    /**
     * 邮箱
     */
    private String email;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户头像
     */
    private String avatar;


    /**
     * 微信号
     */
    private String wechat;

    /**
     * 个性签名
     */
    private String personalSignature;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private Date updateTime;

    /**
     * 是否禁用 01 启用 02未禁用
     */
    private String isDisable;

    /**
     * 性别 1男 0女
     */
    private String sex;

    /**
     * 地区
     */
    private String region;
}
