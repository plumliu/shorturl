package com.plumliu.shorturl.common.convention.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BaseErrorCode implements ErrorCode {
    // 0 表示成功
    SUCCESS(0, "操作成功"),

    // 客户端错误 (400段)
    CLIENT_ERROR(400, "客户端请求错误"),
    PARAM_ERROR(401, "参数校验错误"),
    UNAUTHORIZED(402, "用户未授权"),
    INVALID_TOKEN(403, "Token无效或已过期"),

    // 系统错误 (500段)
    SYSTEM_ERROR(500, "系统内部异常");

    private final Integer code;
    private final String message;

}
