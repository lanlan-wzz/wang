package com.blue.chat.dto.request;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

/**
 * @Description
 * @Author wzz
 * @Date 2021/12/20 15:05
 */
@Data
@Builder
public class UserLoginRequest implements Serializable {

    private static final long serialVersionUID = 6483214819050541892L;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 验证码
     */
    private String code;
}
