package com.blue.chat.service;

import java.util.List;

import com.blue.chat.dto.UserInfoDto;
import com.blue.chat.entity.UserInfoDo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wang
 * @since 2021-12-18
 */
public interface UserInfoService extends IService<UserInfoDo> {
    /**
     * 获取登录用户的详细信息
     * @return 用户信息
     */
    UserInfoDto getUserInfo();

    /**
     * 获取所有用户信息
     * @return List<UserInfoDo>
     */
    List<UserInfoDo> getAllUserInfo();

    /**
     * 根据wechat获取用户信息
     * @param wechat  wechat
     * @return wechat
     */
    UserInfoDto getUserInfo(String wechat);
}
