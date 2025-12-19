package com.plumliu.shorturl.common.convention.exception;

import com.plumliu.shorturl.common.convention.errorcode.ErrorCode;
import lombok.Getter;
import org.springframework.util.StringUtils;

@Getter
public abstract class AbstractException extends RuntimeException {
    public final Integer code;

    public AbstractException(String message, ErrorCode errorCode) {
        super(StringUtils.hasLength(message) ? message + "; " + errorCode.getMessage() : errorCode.getMessage());
        this.code = errorCode.getCode();
    }
}
