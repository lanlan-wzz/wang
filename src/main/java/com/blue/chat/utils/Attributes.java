package com.blue.chat.utils;

import com.blue.chat.entity.UserInfoDo;
import io.netty.util.AttributeKey;

/**
 * 设置channel的属性来判断有没有登录   ---》 channel.attr()
 * @author holiday
 * 2020-10-22
 */
public interface Attributes {
    AttributeKey<UserInfoDo> SESSION = AttributeKey.newInstance("session");
}
