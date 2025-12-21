package com.plumliu.shorturl.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserLoginReqDTO {

    @NotBlank(message = "用户名/邮箱/手机号不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;
}