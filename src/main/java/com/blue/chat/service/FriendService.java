package com.blue.chat.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blue.chat.dto.FriendDto;
import com.blue.chat.entity.FriendDo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wang
 * @since 2021-12-19
 */
public interface FriendService extends IService<FriendDo> {
    /**
     * 根据用户获取朋友列表
     * @return 用户列表
     */
    List<FriendDto> getFriends();
}
