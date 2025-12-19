package com.plumliu.shorturl.common.convention.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserErrorCode implements ErrorCode {

    USER_NULL(10000, "用户不存在"),
    USERNAME_EXIST(10001, "用户名已存在"),
    USER_EXIST(10002, "用户记录已存在"),
    PHONE_EXIST(10003, "手机号已存在"),
    MAIL_EXIST(10004, "邮箱已存在");

    private final Integer code;
    private final String message;
}
