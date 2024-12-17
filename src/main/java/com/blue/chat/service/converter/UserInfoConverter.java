package com.blue.chat.service.converter;

import org.springframework.beans.BeanUtils;

import com.blue.chat.dto.UserInfoDto;
import com.blue.chat.entity.UserInfoDo;

/**
 * @Author wzz
 * @Date 2022/12/3 17:37
 */
public class UserInfoConverter {

    public static UserInfoDto do2Dto(UserInfoDo userInfo){
        if(userInfo==null){
            return null;
        }
        UserInfoDto userInfoDto = new UserInfoDto();
        BeanUtils.copyProperties(userInfo,userInfoDto) ;
        return userInfoDto;
    }
}
