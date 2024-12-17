package com.blue.chat.protocol;

import com.blue.chat.Enum.EnumChatType;

/**
 * @author 26020
 * 发送群聊消息协议
 */
public class GroupSendMessageProtocol extends AbstractProtocol {

	/**
	 * 群聊id
	 */
	private String groupId;
    /**
     * 发送者wechat
     */
    private String senderWechat;
	/**
	 * 消息内容
	 */
	private String messageContent;


	public GroupSendMessageProtocol() {
	}

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getSenderWechat() {
        return senderWechat;
    }

    public void setSenderWechat(String senderWechat) {
        this.senderWechat = senderWechat;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    @Override
	public int messageType() {
		// TODO Auto-generated method stub
		return EnumChatType.GROUP_MESSAGE_REQUEST.getCode();
	}
}
