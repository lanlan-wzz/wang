package com.blue.chat;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 26020
 */
@SpringBootApplication
@MapperScan("com.blue.chat.mapper")
public class WeChatApplication {

    public static void main(String[] args) {
        // 启动应用
        SpringApplication.run(WeChatApplication.class, args);
    }

}
