package com.blue.chat.server;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blue.chat.Enum.EnumChatType;
import com.blue.chat.Enum.EnumHttpStatus;
import com.blue.chat.entity.UserInfoDo;
import com.blue.chat.protocol.AbstractProtocol;
import com.blue.chat.protocol.AddFriendProtocol;
import com.blue.chat.protocol.GroupCreateProtocol;
import com.blue.chat.protocol.GroupSendMessageProtocol;
import com.blue.chat.protocol.HeartBeatProtocol;
import com.blue.chat.protocol.MessageSendProtocol;
import com.blue.chat.protocol.RegisterProtocol;
import com.blue.chat.protocol.RequestMessage;
import com.blue.chat.service.UserInfoService;
import com.blue.chat.utils.SpringBeanUtils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import io.netty.util.CharsetUtil;

/**
 * @author 26020
 */
@Sharable
public class HttpRequestHandler extends SimpleChannelInboundHandler<Object> {

    private final Logger log = LoggerFactory.getLogger(getClass());

    public static HttpRequestHandler INSTANCE = new HttpRequestHandler();

    private WebSocketServerHandshaker webSocketServerHandshaker;

    private HttpRequestHandler() {

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest) {
            handleHttpRequest(ctx, (FullHttpRequest) msg);
            UserInfoService userInfoService = SpringBeanUtils.getBean(UserInfoService.class);
            List<UserInfoDo> allUserInfo = userInfoService.getAllUserInfo();
            allUserInfo.forEach(u -> log.info(JSON.toJSONString(u)));
            log.info("http 握手成功");
        } else if (msg instanceof WebSocketFrame) {
            //fireChannelRead 传递给下一个handler
            handWebsocketFrame(ctx, (WebSocketFrame) msg);
        }
    }

    private void handWebsocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) {
        //判断是否是关闭websocket的指令
        if (frame instanceof CloseWebSocketFrame) {
            webSocketServerHandshaker.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
            return;
        }
        //判断是否是ping消息
        if (frame instanceof PingWebSocketFrame) {
            ctx.channel()
                .write(new PongWebSocketFrame(frame.content()
                    .retain()));
            return;
        }
        TextWebSocketFrame textWebSocketFrame = (TextWebSocketFrame) frame;
        ByteBuf bytebuf = textWebSocketFrame.content();
        String content = bytebuf.toString(StandardCharsets.UTF_8);
        RequestMessage message;
        try {
            message = JSONObject.parseObject(content, RequestMessage.class);
        } catch (Exception e) {
            throw new RuntimeException("消息格式不正确");
        }
        int type = message.getType();
        String body = JSON.toJSONString(message.getBody());
        AbstractProtocol protocol;
        switch (Objects.requireNonNull(EnumChatType.find(type))) {
            // 注册user-->channel 映射
            case REGISTER_REQUEST:
                RegisterProtocol registerProtocol = new RegisterProtocol();
                UserInfoDo user = JSON.parseObject(body, UserInfoDo.class);
                log.info("收到用户注册channel请求,用户wechat:[{}]", user.getWechat());
                // // 校验token
                // String token = message.getToken();
                // if (token == null) {
                //     throw new RuntimeException("token格式错误");
                // }
                // JwtTokenUtil jwtTokenUtil = SpringBeanUtils.getBean(JwtTokenUtil.class);
                // try {
                //     jwtTokenUtil.getClaimFromToken(token);
                // } catch (Exception e) {
                //     throw new RuntimeException("token不合法");
                // }
                registerProtocol.setUser(user);
                protocol = registerProtocol;
                break;
            // 单聊
            case MESSAGE_REQUEST:
                protocol = JSON.parseObject(body, MessageSendProtocol.class);
                break;
            // 创建群聊
            case CREATE_GROUP_REQUEST:
                protocol = JSON.parseObject(body, GroupCreateProtocol.class);
                break;
            // 群聊消息
            case GROUP_MESSAGE_REQUEST:
                protocol = JSON.parseObject(body, GroupSendMessageProtocol.class);
                break;
            //心跳检测
            case HEARTBEAT_REQUEST:
                protocol = new HeartBeatProtocol();
                break;
            //添加好友
            case ADD_FRIEND_MESSAGE:
                protocol = JSON.parseObject(body, AddFriendProtocol.class);
                break;
            default:
                throw new RuntimeException("未知的消息类型");
        }
        ctx.fireChannelRead(protocol);
    }

    /**
     * 描述：处理Http请求，主要是完成HTTP协议到Websocket协议的升级
     *
     * @param ctx
     * @param req
     */
    private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req) {
        if (!req.decoderResult()
            .isSuccess()) {
            sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
            return;
        }

        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory("ws:/" + ctx.channel() + "/websocket", null, false);
        this.webSocketServerHandshaker = wsFactory.newHandshaker(req);
        if (webSocketServerHandshaker == null) {
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
        } else {
            webSocketServerHandshaker.handshake(ctx.channel(), req);
        }
    }

    private void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest req, DefaultFullHttpResponse res) {
        // 返回应答给客户端
        if (res.status()
            .code() != EnumHttpStatus.SUCCESS.getCode()) {
            ByteBuf buf = Unpooled.copiedBuffer(res.status()
                .toString(), CharsetUtil.UTF_8);
            res.content()
                .writeBytes(buf);
            buf.release();
        }
        // 如果是非Keep-Alive，关闭连接
        boolean keepAlive = HttpUtil.isKeepAlive(req);
        ChannelFuture f = ctx.channel()
            .writeAndFlush(res);
        if (!keepAlive) {
            f.addListener(ChannelFutureListener.CLOSE);
        }
    }

    /**
     * 描述：异常处理，关闭channel
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
