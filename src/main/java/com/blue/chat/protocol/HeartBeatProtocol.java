package com.blue.chat.protocol;

import com.blue.chat.Enum.EnumChatType;

/**
 * @author 26020
 * 心跳
 */
public class HeartBeatProtocol extends AbstractProtocol {

	@Override
	public int messageType() {
		return EnumChatType.HEARTBEAT_REQUEST.getCode();
	}
}
