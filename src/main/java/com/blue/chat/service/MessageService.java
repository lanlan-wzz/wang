package com.blue.chat.service;

import com.blue.chat.entity.MessageDo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wang
 * @since 2021-12-24
 */
public interface MessageService extends IService<MessageDo> {

    /**
     * 获取历史消息
     * @return
     */
    List<MessageDo> getMessage();

    /**
     * 更新签收状态
     * @param wechat
     */
    void updateMessage(@Param("wechat") String wechat);
}
