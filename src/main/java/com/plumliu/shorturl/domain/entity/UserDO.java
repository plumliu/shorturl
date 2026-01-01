package com.plumliu.shorturl.domain.entity;


import com.baomidou.mybatisplus.annotation.*;
import lombok.*;


@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_user")
@NoArgsConstructor
@AllArgsConstructor
public class UserDO extends BaseDO {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String username;

    private String password;

    private String realName;

    private String phone;

    private String mail;

    private Long deletionTime;
}
