package com.blue.chat.manager;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.util.StringUtils;

import com.blue.chat.entity.UserInfoDo;
import com.blue.chat.utils.Attributes;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;

/**
 * @author 26020
 */
public class ChatManager {
	/**
	 * wechat 映射 连接channel
	 */
	private static final ConcurrentHashMap<String, Channel> USER_CHANNEL_MAP = new ConcurrentHashMap<>();
	
	/**
	 * groupId ---> channelGroup 群聊ID和群聊ChannelGroup映射
	 */
	private static final ConcurrentHashMap<String, ChannelGroup> GROUP_ID_CHANNEL_GROUP_MAP = new ConcurrentHashMap<>();
	
	public static void bindChannel(UserInfoDo user, Channel channel) {
        USER_CHANNEL_MAP.put(user.getWechat(), channel);
		channel.attr(Attributes.SESSION).set(user);
	}
	
	public static void unbind(Channel channel) {
		if (hasLogin(channel)) {
            USER_CHANNEL_MAP.remove(getUser(channel).getWechat());
			channel.attr(Attributes.SESSION).set(null);
		}
	}
	
	public static boolean hasLogin(Channel channel) {
		return channel.hasAttr(Attributes.SESSION);
	}
	
	public static UserInfoDo getUser(Channel channel) {
		return channel.attr(Attributes.SESSION).get();
	}

	/**
	 * 根据wechat号获取channel
	 * @param wechat wechat号
	 * @return channel
	 */
	public static Channel getChannel(String wechat) {
		return USER_CHANNEL_MAP.get(wechat);
	}
	/**
	 * 绑定群聊Id  群聊channelGroup
	 * @param groupId 群聊id
	 * @param channelGroup 群组
	 */
	public static void bindChannelGroup(String groupId, ChannelGroup channelGroup) {
        GROUP_ID_CHANNEL_GROUP_MAP.put(groupId, channelGroup);
	}

	public static ChannelGroup getChannelGroup(String groupId) {
        if(StringUtils.hasText(groupId)){
            return GROUP_ID_CHANNEL_GROUP_MAP.get(groupId);
        }
		return null;
	}
}
