package com.blue.chat.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blue.chat.common.R;
import com.blue.chat.dto.FriendDto;
import com.blue.chat.service.FriendService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wang
 * @since 2021-12-19
 */
@RestController
@RequestMapping("/user/friend")
public class FriendController {

    @Autowired
    private FriendService friendService;

    @GetMapping("/getFriends")
    public R<List<FriendDto>> getFriends(){
        return  R.ok(friendService.getFriends());
    }
}
