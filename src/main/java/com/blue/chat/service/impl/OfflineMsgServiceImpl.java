package com.blue.chat.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blue.chat.entity.OfflineMsgDo;
import com.blue.chat.mapper.OfflineMsgMapper;
import com.blue.chat.service.OfflineMsgService;
import com.blue.chat.utils.JwtTokenUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wang
 * @since 2021-12-24
 */
@Service
@Slf4j
public class OfflineMsgServiceImpl extends ServiceImpl<OfflineMsgMapper, OfflineMsgDo> implements OfflineMsgService {

    @Autowired
    JwtTokenUtil jwtTokenUtil;
    @Autowired
    OfflineMsgMapper offlineMsgMapper;

    @Autowired
    HttpServletRequest request;
    @Override
    public List<OfflineMsgDo> getOfflineMsg() {
        String wechat=jwtTokenUtil.getWechatToken(request);
        return offlineMsgMapper.selectList(new LambdaQueryWrapper<OfflineMsgDo>().eq(OfflineMsgDo::getReceiverWechat, wechat));
    }
}
