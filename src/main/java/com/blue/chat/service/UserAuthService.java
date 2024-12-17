package com.blue.chat.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blue.chat.dto.LoginDto;
import com.blue.chat.dto.request.UserLoginRequest;
import com.blue.chat.entity.UserAuthDo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wang
 * @since 2021-12-18
 */
public interface UserAuthService extends IService<UserAuthDo> {

    /**
     * 根据用户名查找用户
     * @param username 用户名
     * @return 用户
     */
    UserAuthDo getUserByUsername(String username);

    /**
     * 登录
     * @param loginVo 用户信息
     * @return 用户
     */
    LoginDto login(UserLoginRequest loginVo);

    /**
     * 用户注册
     * @param loginVo 账号和密码
     */
    void registerUser(UserLoginRequest loginVo);
}
