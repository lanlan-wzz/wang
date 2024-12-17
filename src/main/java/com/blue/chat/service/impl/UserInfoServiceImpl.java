package com.blue.chat.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blue.chat.dto.FriendDto;
import com.blue.chat.dto.UserInfoDto;
import com.blue.chat.entity.UserAuthDo;
import com.blue.chat.entity.UserInfoDo;
import com.blue.chat.execption.BaseChatException;
import com.blue.chat.mapper.FriendMapper;
import com.blue.chat.mapper.UserInfoMapper;
import com.blue.chat.service.UserInfoService;
import com.blue.chat.manager.UserHolder;
import com.blue.chat.service.converter.UserInfoConverter;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wang
 * @since 2021-12-18
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfoDo> implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private FriendMapper friendMapper;
    @Override
    public UserInfoDto getUserInfo() {
        UserAuthDo auth = UserHolder.getUserLoginInfo();
        UserInfoDo userInfo = userInfoMapper.selectOne(new LambdaQueryWrapper<UserInfoDo>().eq(UserInfoDo::getUserInfoId, auth.getUserInfoId()));
        //Todo 朋友列表
        List<FriendDto> friends = new ArrayList<>();
        return UserInfoDto.builder()
                .avatar(userInfo.getAvatar())
                .createTime(auth.getCreateTime())
                .email(userInfo.getEmail())
                .friendList(friends)
                .userId(auth.getUserId())
                .userInfoId(userInfo.getUserInfoId())
                .nickname(userInfo.getNickname())
                .sex(userInfo.getSex())
                .username(auth.getUsername())
                .wechat(userInfo.getWechat())
                .region(userInfo.getRegion())
                .personalSignature(userInfo.getPersonalSignature())
                .updateTime(userInfo.getUpdateTime())
                .isDisable(userInfo.getIsDisable())
                .build();
    }

    @Override
    public List<UserInfoDo> getAllUserInfo() {
        return this.userInfoMapper.selectList(new LambdaQueryWrapper<UserInfoDo>().select());
    }

    @Override
    public UserInfoDto getUserInfo(String wechat) {
        if(StringUtils.isEmpty(wechat)){
            throw new BaseChatException("wechat不能为空");
        }
        UserInfoDo userInfoDo = userInfoMapper.selectOne(new LambdaQueryWrapper<UserInfoDo>().eq(UserInfoDo::getWechat, wechat));
        return  UserInfoConverter.do2Dto(userInfoDo);
    }
}
