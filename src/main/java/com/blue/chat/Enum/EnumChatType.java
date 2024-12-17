package com.blue.chat.Enum;

/**
 * @author 26020
 */

public enum EnumChatType {

    /**
     * 发送消息 1
     */
    MESSAGE_REQUEST(1),
    /**
     * 响应消息 2
     */
    MESSAGE_RESPONSE(2),
    /**
     * 创建群 3
     */
    CREATE_GROUP_REQUEST(3),
    /**
     * 创建群响应 4
     */
    CREATE_GROUP_RESPONSE(4),
    /**
     * 登录请求 5
     */
    LOGIN_REQUEST(5),
    /**
     * 登录响应 6
     */
    LOGIN_RESPONSE(6),
    /**
     * 注册请求 7
     */
    REGISTER_REQUEST(7),
    /**
     * 注册响应 8
     */
    REGISTER_RESPONSE(8),
    /**
     * 发送群消息 9
     */
    GROUP_MESSAGE_REQUEST(9),
    /**
     * 接收群消息 10
     */
    GROUP_MESSAGE_RESPONSE(10),
    /**
     * 心跳消息请求 11
     */
    HEARTBEAT_REQUEST(11),
    /**
     * 心跳消息恢复 12
     */
    HEARTBEAT_RESPONSE(12),

    /**
     * 添加好友
     */
    ADD_FRIEND_MESSAGE(13),

    /**
     * 异常
     */
    ERROR(14);
    private final int code;

    EnumChatType(Integer code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
    public static EnumChatType find(Integer code){
        for(EnumChatType type : values()){
            if (type.getCode() == code) {
                return type;
            }
        }
        return null;
    }

}
