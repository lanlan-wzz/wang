package com.blue.chat.server;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.blue.chat.common.R;
import com.blue.chat.entity.GroupDo;
import com.blue.chat.entity.GroupUserDo;
import com.blue.chat.manager.ChatManager;
import com.blue.chat.protocol.GroupCreateProtocol;
import com.blue.chat.service.GroupService;
import com.blue.chat.service.GroupUserService;
import com.blue.chat.utils.SpringBeanUtils;
import com.blue.chat.utils.UUIDUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import static com.blue.chat.constant.BasicConstants.GROUP_DEFAULT_AVATAR;

/**
 * 创建群ChannelHandler组件
 *
 * @author 26020
 */
@Sharable
public class GroupCreateProtocolHandler extends SimpleChannelInboundHandler<GroupCreateProtocol> {

    private final Logger log = LoggerFactory.getLogger(getClass());
    public static GroupCreateProtocolHandler INSTANCE = new GroupCreateProtocolHandler();

    private GroupCreateProtocolHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupCreateProtocol groupCreateProtocol) {
        String creatorWechat = groupCreateProtocol.getCreatorWechat();
        String groupAvatar = groupCreateProtocol.getGroupAvatar();
        String groupName = groupCreateProtocol.getGroupName();
        Set<String> userWechatSet = groupCreateProtocol.getUserWechatSet();
        log.info("接收到创建群组协议,创建者:[{}],群名称:[{}],群人数:[{}]", creatorWechat, groupName, userWechatSet.size());
        if (userWechatSet.size() == 0) {
            return;
        }
        ChannelGroup channelGroup = new DefaultChannelGroup(ctx.executor());
        String groupId = UUIDUtil.getUUID();
        channelGroup.add(ctx.channel());
        userWechatSet.add(ChatManager.getUser(ctx.channel())
            .getWechat());
        for (String wechat : userWechatSet) {
            if (ChatManager.getChannel(wechat) != null) {
                Channel channel = ChatManager.getChannel(wechat);
                channelGroup.add(channel);
            }
        }
        // 绑定群Id 和 channelGroup的映射
        ChatManager.bindChannelGroup(groupId, channelGroup);

        GroupService groupService = SpringBeanUtils.getBean(GroupService.class);
        //创建群
        groupService.save(GroupDo.builder()
            .createTime(new Date())
            .creatorWechat(creatorWechat)
            .groupId(groupId)
            .groupName(groupName == null ? userWechatSet.toString()
                .substring(0, 9) : groupName)
            .numPeople(userWechatSet.size())
            .groupAvatar(groupAvatar == null ? GROUP_DEFAULT_AVATAR : groupAvatar)
            .build());

        GroupUserService groupUserService = SpringBeanUtils.getBean(GroupUserService.class);
        //存入群用户
        List<GroupUserDo> users = userWechatSet.stream()
            .map(wechat -> (GroupUserDo.builder()
                .createTime(new Date())
                .groupId(groupId)
                .wechat(wechat)
                .build()))
            .collect(Collectors.toList());
        groupUserService.saveBatch(users);
        ByteBuf byteBuf = getByteBuf(ctx, groupId);
        channelGroup.writeAndFlush(new TextWebSocketFrame(byteBuf));
        log.info("群创建完成");
    }

    public ByteBuf getByteBuf(ChannelHandlerContext ctx, String groupId) {
        ByteBuf bytebuf = ctx.alloc()
            .buffer();
        byte[] bytes = JSON.toJSONString(R.ok(groupId))
            .getBytes(StandardCharsets.UTF_8);
        bytebuf.writeBytes(bytes);
        return bytebuf;
    }
}
