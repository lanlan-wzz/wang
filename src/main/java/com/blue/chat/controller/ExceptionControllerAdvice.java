package com.blue.chat.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.blue.chat.common.R;
import com.blue.chat.execption.BaseChatException;

/**
 * @Author wzz
 * @Date 2022/12/2 9:58
 */
@ControllerAdvice
public class ExceptionControllerAdvice {
    private static final Logger log = LoggerFactory.getLogger(ExceptionControllerAdvice.class);

    @ExceptionHandler(BaseChatException.class)
    @ResponseBody
    public R<Void> handError(BaseChatException e){
        log.error("出现异常"+e);
        return R.error(e.getMessage());
    }
}
