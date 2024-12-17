package com.blue.chat.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blue.chat.dto.FriendDto;
import com.blue.chat.entity.FriendDo;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wang
 * @since 2021-12-19
 */
@Repository
public interface FriendMapper extends BaseMapper<FriendDo> {

    /**
     * 查询朋友列表
     * @param wechat
     * @return
     */
    List<FriendDto> getFriends(@Param("wechat") String wechat);
}
