package com.blue.chat.service.impl;

import com.blue.chat.entity.GroupDo;
import com.blue.chat.mapper.GroupMapper;
import com.blue.chat.service.GroupService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wang
 * @since 2021-12-24
 */
@Service
public class GroupServiceImpl extends ServiceImpl<GroupMapper, GroupDo> implements GroupService {

}
