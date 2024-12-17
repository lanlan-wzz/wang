package com.blue.chat.manager;

import java.util.Objects;

import org.springframework.security.core.context.SecurityContextHolder;

import com.blue.chat.entity.UserAuthDo;
import com.blue.chat.execption.BaseChatException;

/**
 * @Description
 * @Author wzz
 * @Date 2021/7/27 7:41
 */
public class UserHolder {

    public static UserAuthDo getUserLoginInfo(){
        if (null==SecurityContextHolder.getContext().getAuthentication().getPrincipal()) {
            throw  new BaseChatException("用户已下线");
        }
        return (UserAuthDo)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static String getUserWechat(){
        return Objects.requireNonNull(getUserLoginInfo()).getWechat();
    }
}
