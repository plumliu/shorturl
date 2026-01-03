package com.plumliu.shorturl.common.convention.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GroupErrorCode implements ErrorCode {
    DEFAULT_GROUP_NOT_EXISTS(30000, "默认分组不存在");

    private final Integer code;
    private final String message;
}
