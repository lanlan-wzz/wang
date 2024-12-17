package com.blue.chat.server;

import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.blue.chat.protocol.RequestMessage;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import static com.blue.chat.Enum.EnumChatType.ERROR;

/**
 * Netty异常处理机制
 *
 * @author 26020
 */
@Sharable
public class ExceptionHandler extends ChannelDuplexHandler {

    public static ExceptionHandler INSTANCE = new ExceptionHandler();

    private static final Logger log = LoggerFactory.getLogger(ExceptionHandler.class);

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // TODO Auto-generated method stub
        if (cause instanceof RuntimeException) {
            cause.printStackTrace();
            log.info("pipeline全局异常处理 Handle Business Exception Success.");
            ByteBuf byteBuf = ctx.alloc()
                .buffer();
            RequestMessage messageDTO = RequestMessage.builder()
                .status(500)
                .type(ERROR.getCode())
                .build();
            byte[] bytes = JSONObject.toJSONString(messageDTO)
                .getBytes(StandardCharsets.UTF_8);
            byteBuf.writeBytes(bytes);
            ctx.channel()
                .writeAndFlush(new TextWebSocketFrame(byteBuf));
        }
    }
}

