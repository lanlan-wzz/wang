package com.blue.chat.execption;


/**
 * @Author wzz
 * @Date 2022/12/2 9:47
 */
public class BaseChatException extends RuntimeException{
    public BaseChatException() {
        super();
    }

    public BaseChatException(String message) {
        super(message);
    }

    public BaseChatException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseChatException(Throwable cause) {
        super(cause);
    }

    public BaseChatException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
