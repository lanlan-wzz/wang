package com.blue.chat.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blue.chat.entity.OfflineMsgDo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wang
 * @since 2021-12-24
 */
public interface OfflineMsgService extends IService<OfflineMsgDo> {

    /**
     * 获取用户离线消息
     * @param
     * @return
     */
    List<OfflineMsgDo> getOfflineMsg();
}
