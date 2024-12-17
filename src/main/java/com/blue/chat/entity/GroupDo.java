package com.blue.chat.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author wang
 * @since 2021-12-24
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_group")
public class GroupDo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "group_id", type = IdType.AUTO)
    private String groupId;

    /**
     * 创建者id
     */
    private String creatorWechat;

    /**
     * 群聊人数
     */
    private Integer numPeople;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 群名称
     */
    private String groupName;

    /**
     * 群头像
     */
    private String groupAvatar;

}
