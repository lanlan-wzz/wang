package com.blue.chat.server;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.blue.chat.entity.MessageDo;
import com.blue.chat.entity.OfflineMsgDo;
import com.blue.chat.manager.ChatManager;
import com.blue.chat.protocol.MessageSendProtocol;
import com.blue.chat.service.MessageService;
import com.blue.chat.service.OfflineMsgService;
import com.blue.chat.utils.SpringBeanUtils;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * @author 26020
 */
@Sharable
@Component
public class MessageSendProtocolHandler extends SimpleChannelInboundHandler<MessageSendProtocol> {

    private final Logger log = LoggerFactory.getLogger(getClass());
    public static MessageSendProtocolHandler INSTANCE = new MessageSendProtocolHandler();

    private MessageSendProtocolHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageSendProtocol messageSendProtocol) {
        String messageContent = messageSendProtocol.getMessageContent();
        String receiverWechat = messageSendProtocol.getReceiverWechat();
        String senderWechat = messageSendProtocol.getSenderWechat();
        log.info("接收到单聊消息发送协议,发送者:[{}],接收者:[{}],消息内容:[{}]", senderWechat, receiverWechat, messageContent);

        Channel channel = ChatManager.getChannel(receiverWechat);

        //接收者在线
        if (channel != null && ChatManager.hasLogin(channel)) {
            log.info("接收者:[{}]在线,开始发送消息", receiverWechat);
            ByteBuf buf = getByteBuf(ctx, messageSendProtocol);
            channel.writeAndFlush(new TextWebSocketFrame(buf));
            log.info("消息发送完成");
            // TODO ack
        } else {
            // 接收者不在线 落离线库
            OfflineMsgService offlineMsgService = SpringBeanUtils.getBean(OfflineMsgService.class);
            offlineMsgService.save(OfflineMsgDo.builder()
                .createTime(new Date())
                .msgType("01")
                .msgContent(messageContent)
                .senderWechat(senderWechat)
                .receiverWechat(receiverWechat)
                .build());
        }
        MessageService messageService = SpringBeanUtils.getBean(MessageService.class);
        // 落总消息库
        messageService.save(MessageDo.builder()
            .createTime(new Date())
            .msgContent(messageContent)
            //默认未接收
            .sign("01")
            .msgType("01")
            .receiverWechat(receiverWechat)
            .senderWechat(senderWechat)
            .sendTime(new Date())
            .build());
    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx, MessageSendProtocol messageSendProtocol) {
        ByteBuf byteBuf = ctx.alloc()
            .buffer();
        byte[] bytes = JSON.toJSONString(messageSendProtocol)
            .getBytes(StandardCharsets.UTF_8);
        byteBuf.writeBytes(bytes);
        return byteBuf;
    }
}
