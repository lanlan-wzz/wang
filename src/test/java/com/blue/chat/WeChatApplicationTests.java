package com.blue.chat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.alibaba.fastjson.JSON;
import com.blue.chat.protocol.AddFriendProtocol;
import com.blue.chat.utils.JwtTokenUtil;

@SpringBootTest
class WeChatApplicationTests {

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Test
    void contextLoads() {
    }
    @Test
    public  void test(){
        AddFriendProtocol protocol = new AddFriendProtocol();
        protocol.setRemarks("1");
        protocol.setSenderWechat("1");
        protocol.setReceiverWechat("1");
        System.out.println(JSON.toJSONString(protocol));
    }

}
