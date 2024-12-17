package com.blue.chat.protocol;

import com.blue.chat.Enum.EnumChatType;

/**
 * @author 26020
 */
public class MessageResponseProtocol extends AbstractProtocol {
    /**
     * 发送者weChat
     */
    private String senderWechat;
    /**
     * 接受者Wechat
     */
    private String receiverWechat;
    /**
     * 消息内容
     */
    private String messageContent;

    public String getSenderWechat() {
        return senderWechat;
    }

    public void setSenderWechat(String senderWechat) {
        this.senderWechat = senderWechat;
    }

    public String getReceiverWechat() {
        return receiverWechat;
    }

    public void setReceiverWechat(String receiverWechat) {
        this.receiverWechat = receiverWechat;
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
		return EnumChatType.MESSAGE_RESPONSE.getCode();
	}

}
