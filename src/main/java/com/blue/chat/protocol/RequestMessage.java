package com.blue.chat.protocol;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description TODO
 * @Author wzz
 * @Date 2021/12/22 14:22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestMessage {
    /**
     * 消息类型
     */
    private Integer type;

    /**
     * 状态码
     */
    private Integer status;

    /**
     * token
     */
    private  String token;
    /**
     * 参数
     */
    private Object body;
}
