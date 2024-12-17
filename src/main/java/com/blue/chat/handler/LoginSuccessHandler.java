package com.blue.chat.handler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.blue.chat.common.R;
import com.blue.chat.entity.UserAuthDo;
import com.blue.chat.manager.UserHolder;

/**
 * @Description
 * @Author wzz
 * @Date 2021/7/27 7:09
 */
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication)
    throws IOException, ServletException {
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        ServletOutputStream os = httpServletResponse.getOutputStream();
        UserAuthDo userLoginInfo = UserHolder.getUserLoginInfo();
        userLoginInfo.setPassword(null);
        os.write(JSON.toJSONString(R.ok("登录成功", userLoginInfo))
            .getBytes(StandardCharsets.UTF_8));
        os.flush();
        os.close();
    }
}
