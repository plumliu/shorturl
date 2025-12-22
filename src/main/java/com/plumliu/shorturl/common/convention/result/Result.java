package com.plumliu.shorturl.common.convention.result;

import com.plumliu.shorturl.common.convention.errorcode.BaseErrorCode;
import com.plumliu.shorturl.common.convention.errorcode.ErrorCode;
import lombok.Builder;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Builder
public class Result<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final Integer code;

    private final String message;

    private final T data;

    private final String requestId;

    public static Result<Void> success(String requestId) {
        return Result.<Void>builder()
                .code(BaseErrorCode.SUCCESS.getCode())
                .message(BaseErrorCode.SUCCESS.getMessage())
                .requestId(requestId)
                .build();
    }

    public static <T> Result<T> success(T data, String requestId) {
        return Result.<T>builder()
                .code(BaseErrorCode.SUCCESS.getCode())
                .message(BaseErrorCode.SUCCESS.getMessage())
                .data(data)
                .requestId(requestId)
                .build();
    }

    public static Result<Void> failure(Integer code, String message) {
        return Result.<Void>builder()
                .code(code)
                .message(message)
                .build();
    }

    public Result<T> withRequestId(String requestId) {
        return Result.<T>builder()
                .code(code)
                .message(message)
                .data(data)
                .requestId(requestId)
                .build();
    }
}
