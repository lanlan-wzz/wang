package com.blue.chat.Enum;

/**
 * @author wzz26336@yunrong.cn
 * @version V2.1
 * @since 2022/11/17 19:14
 */
public enum EnumHttpStatus {
    /**
     * 成功
     */
    SUCCESS(200,"成功"),


    FAIL(500,"失败");

    /**
     * 状态码
     */
    private final int code;
    /**
     * 描述
     */
    private final String desc;

    EnumHttpStatus(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
