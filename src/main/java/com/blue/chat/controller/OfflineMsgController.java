package com.blue.chat.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blue.chat.common.R;
import com.blue.chat.entity.OfflineMsgDo;
import com.blue.chat.service.OfflineMsgService;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author wang
 * @since 2021-12-24
 */
@RestController
@RequestMapping("/user/offline")
public class OfflineMsgController {

    @Autowired
    private OfflineMsgService offlineMsgService;

    @GetMapping("/getOfflineMsg")
    public R<List<OfflineMsgDo>> getOfflineMsg() {
        return R.ok(offlineMsgService.getOfflineMsg());
    }
}
