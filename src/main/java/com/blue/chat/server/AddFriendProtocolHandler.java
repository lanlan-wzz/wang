package com.blue.chat.server;

import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.blue.chat.manager.ChatManager;
import com.blue.chat.protocol.AddFriendProtocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * @Author wzz
 * @Date 2022/12/3 17:52
 */
public class AddFriendProtocolHandler extends SimpleChannelInboundHandler<AddFriendProtocol> {
    private final Logger log = LoggerFactory.getLogger(getClass());
    public static AddFriendProtocolHandler INSTANCE = new AddFriendProtocolHandler();

    private AddFriendProtocolHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, AddFriendProtocol msg) {
        String receiverWechat = msg.getReceiverWechat();
        Channel channel = ChatManager.getChannel(receiverWechat);
        if (channel != null && ChatManager.hasLogin(channel)) {
            log.info("接收者:[{}]在线,开始发送消息", receiverWechat);
            ByteBuf buf = getByteBuf(ctx, msg);
            channel.writeAndFlush(new TextWebSocketFrame(buf));
            log.info("消息发送完成");
        }
    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx, AddFriendProtocol msg) {
        ByteBuf byteBuf = ctx.alloc()
            .buffer();
        byte[] bytes = JSON.toJSONString(msg)
            .getBytes(StandardCharsets.UTF_8);
        byteBuf.writeBytes(bytes);
        return byteBuf;
    }
}
