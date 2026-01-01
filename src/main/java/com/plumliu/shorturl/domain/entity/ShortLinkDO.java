package com.plumliu.shorturl.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.*;

import java.time.LocalDateTime;



@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_link")
@NoArgsConstructor
@AllArgsConstructor
public class ShortLinkDO extends BaseDO {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long userId;

    private String domain;

    private String shortUri;

    private String fullShortUrl;

    private String originalUrl;

    private Integer totalPv;

    private Integer totalUv;

    private Integer totalUip;

    private String gid;

    private Integer enableStatus;

    private Integer createType;

    private Integer validDateType;

    private LocalDateTime validDate;

    private String description;

}
