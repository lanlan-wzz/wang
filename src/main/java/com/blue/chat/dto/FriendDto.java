package com.blue.chat.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * @Description TODO
 * @Author wzz
 * @Date 2021/12/19 21:39
 */
@Data
public class FriendDto implements Serializable {
    /**
     * 头像
     */
    private String avatar;
    /**
     * 备注
     */
    private String remarks;
    /**
     * 朋友id
     */
    private String friendId;

}
