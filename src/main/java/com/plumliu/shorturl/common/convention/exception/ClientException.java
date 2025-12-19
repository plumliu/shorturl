package com.plumliu.shorturl.common.convention.exception;

import com.plumliu.shorturl.common.convention.errorcode.BaseErrorCode;
import com.plumliu.shorturl.common.convention.errorcode.ErrorCode;

public class ClientException extends AbstractException {
    public ClientException(ErrorCode errorCode) {
        super(null, errorCode);
    }

    public ClientException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public ClientException(String message) {
        super(message, BaseErrorCode.CLIENT_ERROR);
    }
}
