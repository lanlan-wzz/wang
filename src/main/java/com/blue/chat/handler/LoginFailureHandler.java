package com.blue.chat.handler;

import com.alibaba.fastjson.JSON;
import com.blue.chat.common.R;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @Description TODO
 * @Author wzz
 * @Date 2021/7/27 7:13
 */
@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        ServletOutputStream os = httpServletResponse.getOutputStream();
        os.write(JSON.toJSONString(R.error(401, "登录失败")).getBytes(StandardCharsets.UTF_8));
        os.flush();
        os.close();
    }
}
