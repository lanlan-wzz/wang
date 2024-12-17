package com.blue.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blue.chat.common.R;
import com.blue.chat.dto.LoginDto;
import com.blue.chat.dto.request.UserLoginRequest;
import com.blue.chat.service.UserAuthService;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author wang
 * @since 2021-12-18
 */
@RestController
@RequestMapping("/auth")
@Slf4j
public class UserAuthController {

    @Autowired
    private UserAuthService userAuthService;

    @PostMapping("/login")
    public R<LoginDto> login(@RequestBody UserLoginRequest loginVo) {
        log.info("用户{}登录", loginVo.getUsername());
        return R.ok(userAuthService.login(loginVo));
    }

    @PostMapping("/register")
    public R<Void> userRegister(@RequestBody UserLoginRequest request){
        userAuthService.registerUser(request);
        return R.ok();
    }
}
