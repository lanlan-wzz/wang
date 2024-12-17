package com.blue.chat.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blue.chat.Enum.EnumBoolean;
import com.blue.chat.entity.MessageDo;
import com.blue.chat.mapper.MessageMapper;
import com.blue.chat.service.MessageService;
import com.blue.chat.utils.JwtTokenUtil;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wang
 * @since 2021-12-24
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, MessageDo> implements MessageService {

    @Autowired
    JwtTokenUtil jwtTokenUtil;
    @Autowired
    HttpServletRequest request;
    @Autowired
    private MessageMapper messageMapper;

    @Override
    public List<MessageDo> getMessage() {
        String wechat = jwtTokenUtil.getWechatToken(request);
        return messageMapper.selectList(new LambdaQueryWrapper<MessageDo>().eq(MessageDo::getReceiverWechat, wechat)
            .orderByAsc(MessageDo::getCreateTime));
    }

    @Override
    public void updateMessage(String wechat) {
        String userWechat = jwtTokenUtil.getWechatToken(request);
        messageMapper.update(MessageDo.builder()
            .sign(EnumBoolean.YES.getCode())
            .build(), new LambdaQueryWrapper<MessageDo>().eq(MessageDo::getReceiverWechat, userWechat)
            .eq(MessageDo::getSenderWechat, wechat));
    }
}
