package com.blue.chat.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @Description TODO
 * @Author wzz
 * @Date 2021/12/19 12:48
 */
@Component
public class NettyServer implements InitializingBean {

    private final Logger log = LoggerFactory.getLogger(getClass());
    @Value("${netty.port}")
    private int port;

    @Override
    public void afterPropertiesSet() {
        ServerBootstrap bootstrap = new ServerBootstrap();
        //处理连接工作线程
        EventLoopGroup mainGroup = new NioEventLoopGroup();
        //接收数据线程
        EventLoopGroup subGroup = new NioEventLoopGroup();
        bootstrap.group(mainGroup, subGroup)
            .channel(NioServerSocketChannel.class)
            //服务端可连接队列数,对应tcp/Ip协议listen函数的backlog参数
            .option(ChannelOption.SO_BACKLOG, 1024)
            //设置TCP长连接
            .childOption(ChannelOption.SO_KEEPALIVE, true)
            //数据包包装成更大的帧进行传送,提高网络负载
            .childOption(ChannelOption.TCP_NODELAY, true)
            .childHandler(new ChannelInitializer<NioSocketChannel>() {
                @Override
                protected void initChannel(NioSocketChannel ch) {
                    ch.pipeline()
                        .addLast(new ImIdleStateHandler());
                    ch.pipeline()
                        .addLast("http-codec", new HttpServerCodec()); //http编解码
                    ch.pipeline()
                        .addLast("aggregator", new HttpObjectAggregator(65536)); //httpContent消息聚合
                    ch.pipeline()
                        .addLast("compressor", new HttpContentCompressor()); // HttpContent 压缩
                    ch.pipeline()
                        .addLast("http-chunked", new ChunkedWriteHandler());
                    ch.pipeline()
                        .addLast(HttpRequestHandler.INSTANCE);
                    ch.pipeline()
                        .addLast(RegisterProtocolHandler.INSTANCE);
                    ch.pipeline()
                        .addLast(MessageSendProtocolHandler.INSTANCE);
                    ch.pipeline()
                        .addLast(GroupSendMessageHandler.INSTANCE);
                    ch.pipeline()
                        .addLast(GroupCreateProtocolHandler.INSTANCE);
                    ch.pipeline()
                        .addLast(AddFriendProtocolHandler.INSTANCE);
                    ch.pipeline()
                        .addLast(HeartBeatProtocolHandler.INSTANCE);
                    ch.pipeline()
                        .addLast(ExceptionHandler.INSTANCE);
                }
            });
        bind(bootstrap, port);
    }

    public void bind(final ServerBootstrap serverBootstrap, final int port) {
        serverBootstrap.bind(port)
            .addListener(future -> {
                if (future.isSuccess()) {
                    log.info("NettyServer服务启动成功,端口:[{}]", port);
                } else {
                    log.info("NettyServer服务启动失败,端口:[{}]", port);
                    bind(serverBootstrap, port + 1);
                }
            });
    }
}
