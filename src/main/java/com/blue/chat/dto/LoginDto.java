package com.blue.chat.dto;

import com.blue.chat.entity.UserInfoDo;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Description TODO
 * @Author wzz
 * @Date 2021/12/20 15:18
 */
@Data
@Builder
@Accessors(chain = true)
public class LoginDto {
    /**
     * token头
     */
    private String tokenHead;
    /**
     * token
     */
    private String accessToken;
    /**
     * 用户信息
     */
    private UserInfoDo user;
}
