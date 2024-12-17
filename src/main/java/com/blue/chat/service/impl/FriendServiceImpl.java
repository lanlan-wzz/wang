package com.blue.chat.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blue.chat.dto.FriendDto;
import com.blue.chat.entity.FriendDo;
import com.blue.chat.mapper.FriendMapper;
import com.blue.chat.service.FriendService;
import com.blue.chat.utils.JwtTokenUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wang
 * @since 2021-12-19
 */
@Service
@Slf4j
public class FriendServiceImpl extends ServiceImpl<FriendMapper, FriendDo> implements FriendService {

    @Autowired
    FriendMapper friendMapper;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    HttpServletRequest request;

    @Override
    public List<FriendDto> getFriends() {
        String wechat = jwtTokenUtil.getWechatToken(request);
        return friendMapper.getFriends(wechat);
    }
}
