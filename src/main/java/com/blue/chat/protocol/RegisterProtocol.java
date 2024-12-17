package com.blue.chat.protocol;

import com.blue.chat.Enum.EnumChatType;
import com.blue.chat.entity.UserInfoDo;

/**
 * @author 26020
 */
public class RegisterProtocol extends AbstractProtocol {
    	
	private UserInfoDo user;

	public UserInfoDo getUser() {
		return user;
	}

	public void setUser(UserInfoDo user) {
		this.user = user;
	}

	@Override
	public int messageType() {
		// TODO Auto-generated method stub
		return EnumChatType.REGISTER_REQUEST.getCode();
	}

}
