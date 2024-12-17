package com.blue.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blue.chat.common.R;
import com.blue.chat.dto.UserInfoDto;
import com.blue.chat.service.UserInfoService;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author wang
 * @since 2021-12-18
 */
@RestController
@RequestMapping("/user")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @GetMapping("/userInfo")
    public R<UserInfoDto> getUserInfo() {
        return R.ok(userInfoService.getUserInfo());
    }

    /**
     * 根据wechat查询用户信息
     * @param wechat wechat
     * @return UserInfoDto
     */
    @GetMapping("/userInfo/{wechat}")
    public R<UserInfoDto> getUserInfoByWechat(@PathVariable String wechat) {
        return R.ok(userInfoService.getUserInfo(wechat));
    }
}
