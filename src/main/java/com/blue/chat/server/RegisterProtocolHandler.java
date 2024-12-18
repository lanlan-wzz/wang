package com.blue.chat.server;

import java.nio.charset.StandardCharsets;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.blue.chat.common.R;
import com.blue.chat.entity.GroupUserDo;
import com.blue.chat.entity.UserInfoDo;
import com.blue.chat.manager.ChatManager;
import com.blue.chat.protocol.RegisterProtocol;
import com.blue.chat.service.GroupUserService;
import com.blue.chat.utils.SpringBeanUtils;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * 注册 绑定用户channel
 *
 * @author 26020
 */
@Sharable
public class RegisterProtocolHandler extends SimpleChannelInboundHandler<RegisterProtocol> {

    private final Logger log = LoggerFactory.getLogger(getClass());
    public static RegisterProtocolHandler INSTANCE = new RegisterProtocolHandler();

    private RegisterProtocolHandler() {

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RegisterProtocol registerProtocol) {
        UserInfoDo user = registerProtocol.getUser();
        log.info("接收到用户register Channel,用户weChat[{}]", user.getWechat());
        //给用户创建channel
        ChatManager.bindChannel(user, ctx.channel());
        //上线后查出所有自己所在的群,然后把自己加入
        GroupUserService groupUserService = SpringBeanUtils.getBean(GroupUserService.class);
        List<GroupUserDo> groupUsers = groupUserService.list(new LambdaQueryWrapper<GroupUserDo>().eq(GroupUserDo::getWechat, user.getWechat()));
        groupUsers.forEach(groupUser -> {
            ChannelGroup group = ChatManager.getChannelGroup(groupUser.getGroupId());
            if (group != null) {
                group.add(ctx.channel());
            }
        });

        log.info("用户注册Channel完成");
        if (ChatManager.hasLogin(ctx.channel())) {
            log.debug("[用户{}登录成功]", user.getWechat());
        }
        ByteBuf buffer = ctx.alloc()
                .buffer();
        byte[] bytes = JSON.toJSONString(R.ok("channel注册成功"))
                .getBytes(StandardCharsets.UTF_8);
        buffer.writeBytes(bytes);
        ctx.channel()
                .writeAndFlush(new TextWebSocketFrame(buffer));
    }

}
