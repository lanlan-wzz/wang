package com.blue.chat.protocol;

import com.blue.chat.Enum.EnumChatType;

/**
 * @author 26020
 * 心跳响应
 */
public class HeartBeatResponseProtocol extends AbstractProtocol {

	@Override
	public int messageType() {
		return EnumChatType.HEARTBEAT_RESPONSE.getCode();
	}
}
