package com.blue.chat.server;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * 空闲连接检测   如果规定时间没有数据传输则关闭连接    不能实现为单例模式  每个连接都有各自的状态
 *
 * @author 26020
 */
public class ImIdleStateHandler extends IdleStateHandler {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private static final int READER_IDLE_TIME = 60;

    /**
     * 第一个参数表示读空闲时间
     * 第二个参数表示写空闲时间
     * 第三个参数表示读写空闲时间
     * 第四个参数表示时间单位
     */
    public ImIdleStateHandler() {
        super(READER_IDLE_TIME, 0, 0, TimeUnit.SECONDS);
    }

    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) {
        log.debug("[{}秒内未读到数据,{}连接正常]", READER_IDLE_TIME, ctx.channel()
            .id());
        //        SessionUtils.unbind(ctx.channel());
        //		ctx.channel().close();
    }
}
