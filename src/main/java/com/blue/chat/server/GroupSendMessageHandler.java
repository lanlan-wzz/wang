package com.blue.chat.server;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.blue.chat.entity.GroupMsgDo;
import com.blue.chat.manager.ChatManager;
import com.blue.chat.protocol.GroupSendMessageProtocol;
import com.blue.chat.service.GroupMsgService;
import com.blue.chat.utils.SpringBeanUtils;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * 发送群消息handler组件
 *
 * @author 26020
 */
@Sharable
public class GroupSendMessageHandler extends SimpleChannelInboundHandler<GroupSendMessageProtocol> {

    private final Logger log = LoggerFactory.getLogger(getClass());
    public static GroupSendMessageHandler INSTANCE = new GroupSendMessageHandler();

    private GroupSendMessageHandler() {

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupSendMessageProtocol groupSendMessageProtocol) {
        String groupId = groupSendMessageProtocol.getGroupId();
        String messageContent = groupSendMessageProtocol.getMessageContent();
        String senderWechat = groupSendMessageProtocol.getSenderWechat();
        log.info("接收到群消息发送协议,发送者:[{}]", senderWechat);
        ChannelGroup channelGroup = ChatManager.getChannelGroup(groupId);
        if (channelGroup == null) {
            throw new RuntimeException("群不存在");
        }
        ByteBuf byteBuf = getByteBuf(ctx, groupSendMessageProtocol);
        //发送方不需要自己再收到消息
        channelGroup.remove(ctx.channel());
        channelGroup.writeAndFlush(new TextWebSocketFrame(byteBuf));
        //发送完消息再添加回去 ---todo 是否有更好得方式
        channelGroup.add(ctx.channel());
        //群消息落库
        GroupMsgService groupMsgService = SpringBeanUtils.getBean(GroupMsgService.class);
        groupMsgService.save(GroupMsgDo.builder()
            .groupMsg(messageContent)
            .groupId(groupId)
            .createTime(new Date())
            .build());

    }

    public ByteBuf getByteBuf(ChannelHandlerContext ctx, GroupSendMessageProtocol groupSendMessageProtocol) {
        ByteBuf byteBuf = ctx.alloc()
            .buffer();
        byte[] bytes = JSON.toJSONString(groupSendMessageProtocol)
            .getBytes(StandardCharsets.UTF_8);
        byteBuf.writeBytes(bytes);
        return byteBuf;
    }
}
