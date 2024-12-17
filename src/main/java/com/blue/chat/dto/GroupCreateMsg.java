package com.blue.chat.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description TODO
 * @Author wzz
 * @Date 2021/12/25 17:04
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupCreateMsg {


    /**
     * 群用户
     */
    private List<String> userList;
    /**
     * 群名称
     */
    private String groupName;

    /**
     * 群头像
     */
    private String avatar;
}
