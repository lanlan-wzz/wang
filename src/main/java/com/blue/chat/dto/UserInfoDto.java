package com.blue.chat.dto;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description TODO
 * @Author wzz
 * @Date 2021/12/19 21:30
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDto {


    /**
     * 微信号
     */
    private String wechat;

    /**
     * 用户id
     */
    private String userId;
    /**
     * 用户信息id
     */
    private String userInfoId;
    /**
     * 用户名
     */
    private String username;
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
     * 个性签名
     */
    private String personalSignature;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;


    /**
     * 是否禁用
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


    /**
     * 朋友列表
     */
    private List<FriendDto> friendList;

}
