package com.blue.chat.common;

import java.io.Serializable;

import com.blue.chat.Enum.EnumHttpStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description
 * @Author wzz
 * @Date 2021/12/19 19:58
 */

@Builder
@Getter
@Setter
@SuppressWarnings("all")
public class R<T> implements Serializable {
    private static final long serialVersionUID = 1391615278505854707L;
    private boolean success;
    private int code;
    private String message;
    private T data;

    public static <T> R<T> ok(T data) {
        return R.<T>builder()
            .success(true)
            .code(EnumHttpStatus.SUCCESS.getCode())
            .data(data)
            .build();
    }

    public static <T> R<T> ok(String message, T data) {
        return R.<T>builder()
            .success(true)
            .code(EnumHttpStatus.SUCCESS.getCode())
            .message(message)
            .data(data)
            .build();
    }

    public static <T> R<T> ok() {
        return R.<T>builder()
            .success(true)
            .code(EnumHttpStatus.SUCCESS.getCode())
            .build();
    }

    public static <T> R<T> error(int code, String errorMessage) {
        return R.<T>builder()
            .success(false)
            .code(code)
            .message(errorMessage)
            .build();
    }

    public static <T> R<T> error(String errorMessage) {
        return R.<T>builder()
            .success(false)
            .code(EnumHttpStatus.FAIL.getCode())
            .message(errorMessage)
            .build();
    }

    public R(boolean success, int code, String message, T data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public R(String message, T data) {
        this.message = message;
        this.data = data;
    }
}
