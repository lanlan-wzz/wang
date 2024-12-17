package com.blue.chat.server;

import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.blue.chat.protocol.HeartBeatProtocol;
import com.blue.chat.protocol.RequestMessage;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import static com.blue.chat.Enum.EnumChatType.HEARTBEAT_RESPONSE;

/**
 * @author 26020
 */
@Sharable
public class HeartBeatProtocolHandler extends SimpleChannelInboundHandler<HeartBeatProtocol> {

    private final Logger log = LoggerFactory.getLogger(getClass());
    public static HeartBeatProtocolHandler INSTANCE = new HeartBeatProtocolHandler();

    private HeartBeatProtocolHandler() {

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HeartBeatProtocol heartBeatRequestPacket) {
        ByteBuf byteBuf = getBuf(ctx);
        ctx.channel()
            .writeAndFlush(new TextWebSocketFrame(byteBuf));
    }

    public ByteBuf getBuf(ChannelHandlerContext ctx) {
        ByteBuf byteBuf = ctx.alloc()
            .buffer();
        RequestMessage requestMessage = RequestMessage.builder()
            .status(200)
            .type(HEARTBEAT_RESPONSE.getCode())
            .build();
        byte[] bytes = JSON.toJSONString(requestMessage)
            .getBytes(StandardCharsets.UTF_8);
        byteBuf.writeBytes(bytes);
        return byteBuf;
    }
}
