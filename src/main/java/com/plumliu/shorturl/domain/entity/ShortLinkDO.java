package com.plumliu.shorturl.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@TableName("t_link")
public class ShortLinkDO {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String domain;

    private String shortUri;

    private String fullShortUrl;

    private String originalUrl;

    private Integer clickNum;

    private String gid;

    private Integer enableStatus;

    private Integer createType;

    private Integer validDateType;

    private LocalDateTime validDate;

    private String description;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableLogic
    private Integer deleted;

}
