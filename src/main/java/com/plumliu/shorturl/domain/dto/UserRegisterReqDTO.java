package com.plumliu.shorturl.domain.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRegisterReqDTO {

    @NotBlank(message = "用户名不能为空")
    @Size(min = 2, max = 64, message = "用户名长度需为2-64个字符")
    @Pattern(regexp = "^[a-zA-Z0-9_\\u4e00-\\u9fa5]+$",
            message = "用户名只能包含字母、数字、下划线或中文")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 128, message = "密码长度需为6-128位")
    private String password;

    @NotBlank(message = "真实姓名不能为空")
    @Size(max = 64, message = "姓名长度不能超过64个字符")
    private String realName;

    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String phone;

    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    @Size(max = 256, message = "邮箱长度不能超过256个字符")
    private String mail;

}
