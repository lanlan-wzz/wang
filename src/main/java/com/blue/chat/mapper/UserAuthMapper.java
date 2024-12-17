package com.blue.chat.mapper;

import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blue.chat.entity.UserAuthDo;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wang
 * @since 2021-12-18
 */
@Repository
public interface UserAuthMapper extends BaseMapper<UserAuthDo> {

    /**
     * 查询用户
     * @param username
     * @return
     */
    UserAuthDo getUser(String username);
}
