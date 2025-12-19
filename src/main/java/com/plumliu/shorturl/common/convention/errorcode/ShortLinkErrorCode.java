package com.plumliu.shorturl.common.convention.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ShortLinkErrorCode implements ErrorCode {

    TOO_MANY_ATTEMPTS(20000, "尝试次数过多");

    private final Integer code;
    private final String message;
}