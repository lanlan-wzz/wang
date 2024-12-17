package com.blue.chat.Enum;

/**
 * @author wzz26336@yunrong.cn
 * @version V2.1
 * @since 2022/12/2 17:08
 */
public enum EnumBoolean {
    /**
    *启用
     */
    YES("01","Enable"),
    /**
     * 禁用
     */
    NO("02","Disable");

    /**
     * code
     */
    private final String code;
    /**
     * 描述
     */
    private final String description;

    EnumBoolean(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
