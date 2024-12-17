package com.blue.chat.protocol;

import static com.blue.chat.Enum.EnumChatType.ADD_FRIEND_MESSAGE;

/**
 * @Author wzz
 * @Date 2022/12/3 17:44
 */
public class AddFriendProtocol  extends AbstractProtocol {

    /**
     * 发送者weChat
     */
    private String senderWechat;
    /**
     * 接收者Wechat
     */
    private String receiverWechat;
    /**
     * 备注信息
     */
    private String remarks;

    @Override
    public int messageType() {
        return ADD_FRIEND_MESSAGE.getCode();
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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
