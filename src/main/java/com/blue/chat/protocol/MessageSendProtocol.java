package com.blue.chat.protocol;

import static com.blue.chat.Enum.EnumChatType.MESSAGE_REQUEST;

/**
 * @author 26020
 */
public class MessageSendProtocol extends AbstractProtocol {

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

			
	public MessageSendProtocol() {
		super();
	}

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

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
    @Override
	public int messageType() {
		// TODO Auto-generated method stub
		return MESSAGE_REQUEST.getCode();
	}

}
