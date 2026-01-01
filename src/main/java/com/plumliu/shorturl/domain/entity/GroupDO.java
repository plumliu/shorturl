package com.plumliu.shorturl.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

/**
 * 短链接分组实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_group")
@NoArgsConstructor
@AllArgsConstructor
public class GroupDO extends BaseDO {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String gid;

    private String name;

    private String username;

    private Integer sortOrder;
}