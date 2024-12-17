package com.blue.chat.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blue.chat.common.R;
import com.blue.chat.entity.MessageDo;
import com.blue.chat.service.MessageService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wang
 * @since 2021-12-24
 */
@RestController
@RequestMapping("/user/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @GetMapping("/getMessage")
    public R<List<MessageDo>> getMessage(){
      return R.ok(messageService.getMessage());
    }

    @PutMapping("/status/{wechat}")
    public R<Void> updateMessage(@PathVariable("wechat") String wechat){
        messageService.updateMessage(wechat);
        return R.ok();
    }

}
