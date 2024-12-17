package com.blue.chat.protocol;

import java.util.Set;

import com.blue.chat.Enum.EnumChatType;

/**
 * @author 26020
 */
public class GroupCreateProtocol extends AbstractProtocol {

    /**
     * 创建者wechat (群主)
     */
    private String creatorWechat;
    /**
     * 创建群里需要把群里的Id发过来
     */
	private Set<String> userWechatSet;
	/**
	 * 群名称
	 */
	private String groupName;
	/**
	 * 群头像
	 */
	private String groupAvatar;

    public String getCreatorWechat() {
        return creatorWechat;
    }

    public void setCreatorWechat(String creatorWechat) {
        this.creatorWechat = creatorWechat;
    }

    public Set<String> getUserWechatSet() {
        return userWechatSet;
    }

    public void setUserWechatSet(Set<String> userWechatSet) {
        this.userWechatSet = userWechatSet;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupAvatar() {
        return groupAvatar;
    }

    public void setGroupAvatar(String groupAvatar) {
        this.groupAvatar = groupAvatar;
    }

    @Override
	public int messageType() {
		// TODO Auto-generated method stub
		return EnumChatType.CREATE_GROUP_REQUEST.getCode();
	}

}
