package com.blue.chat.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blue.chat.Enum.EnumBoolean;
import com.blue.chat.dto.LoginDto;
import com.blue.chat.dto.request.UserLoginRequest;
import com.blue.chat.entity.UserAuthDo;
import com.blue.chat.entity.UserInfoDo;
import com.blue.chat.execption.BaseChatException;
import com.blue.chat.mapper.UserAuthMapper;
import com.blue.chat.mapper.UserInfoMapper;
import com.blue.chat.service.UserAuthService;
import com.blue.chat.utils.JwtTokenUtil;
import com.blue.chat.utils.UUIDUtil;
import com.blue.chat.manager.UserHolder;

import static com.blue.chat.constant.BasicConstants.USER_DEFAULT_AVATAR;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wang
 * @since 2021-12-18
 */
@Service
public class UserAuthServiceImpl extends ServiceImpl<UserAuthMapper, UserAuthDo> implements UserAuthService {

    @Autowired
    private UserAuthMapper userAuthMapper;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Override
    public UserAuthDo getUserByUsername(String username) {
        return userAuthMapper.getUser(username);
    }

    @Override
    public LoginDto login(UserLoginRequest request) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        if (userDetails == null) {
            throw new RuntimeException("用户名不存在");
        }
        if (!passwordEncoder.matches(request.getPassword(), userDetails.getPassword())) {
            throw new BaseChatException("账号或密码错误");
        }
        if (!userDetails.isEnabled()) {
            throw new BaseChatException("用户被禁用");
        }
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext()
            .setAuthentication(usernamePasswordAuthenticationToken);
        UserAuthDo auth = UserHolder.getUserLoginInfo();
        if(auth==null){
            throw new BaseChatException("用户不存在");
        }
        UserInfoDo info = userInfoMapper.selectOne(new LambdaQueryWrapper<UserInfoDo>().eq(UserInfoDo::getUserInfoId, auth.getUserInfoId()));
        String token = jwtTokenUtil.generateToken(userDetails);
        return LoginDto.builder()
            .accessToken(token)
            .tokenHead(tokenHead)
            .user(info)
            .build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void registerUser(UserLoginRequest loginVo) {
        UserAuthDo user = userAuthMapper.getUser(loginVo.getUsername());
        if (user != null) {
            throw new BaseChatException("用户已存在");
        }
        //插入用户账号信息
        String userInfoId = UUIDUtil.getUUID();
        UserAuthDo userAuthDo = UserAuthDo.builder()
            .username(loginVo.getUsername())
            .userId(UUIDUtil.getUUID())
            .password(passwordEncoder.encode(loginVo.getPassword()))
            .createTime(new Date())
            .wechat(loginVo.getUsername())
            .loginType("01")
            .userInfoId(userInfoId)
            .build();
        userAuthMapper.insert(userAuthDo);
        //插入用户其他信息
        UserInfoDo userInfoDo = UserInfoDo.builder()
            .nickname(loginVo.getUsername())
            .isDisable(EnumBoolean.YES.getCode())
            .createTime(new Date())
            .userInfoId(userInfoId)
            .wechat(loginVo.getUsername())
            .nickname(loginVo.getUsername())
            .avatar(USER_DEFAULT_AVATAR)
            .build();
        userInfoMapper.insert(userInfoDo);
    }
}
