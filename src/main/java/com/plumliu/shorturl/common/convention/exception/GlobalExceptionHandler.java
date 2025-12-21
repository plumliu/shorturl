package com.plumliu.shorturl.common.convention.exception;


import com.plumliu.shorturl.common.convention.errorcode.BaseErrorCode;
import com.plumliu.shorturl.common.convention.result.Result;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Optional;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        FieldError fieldError = bindingResult.getFieldError();
        String errorMessage = Optional.ofNullable(fieldError)
                .map(FieldError::getDefaultMessage)
                .orElse("未知参数校验异常");
        log.warn("参数校验异常: {}", errorMessage);
        return Result.failure(BaseErrorCode.PARAM_ERROR.getCode(), BaseErrorCode.PARAM_ERROR.getMessage() + "：" + errorMessage);
    }

    @ExceptionHandler(value = ClientException.class)
    public Result<Void> handleClientException(ClientException ex) {
        return Result.failure(ex.getCode(), ex.getMessage());
    }

    @ExceptionHandler(value = Throwable.class)
    public Result<Void> handleThrowable(Throwable set, HttpServletRequest request) {
        log.error("系统内部异常: URL={}", request.getRequestURI(), set);
        return Result.failure(BaseErrorCode.SYSTEM_ERROR.getCode(), BaseErrorCode.SYSTEM_ERROR.getMessage());
    }

}
