package com.plumliu.shorturl.common.convention.result;

import cn.hutool.json.JSONUtil;
import com.plumliu.shorturl.common.convention.errorcode.BaseErrorCode;
import com.plumliu.shorturl.common.convention.exception.ClientException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Optional;

@Slf4j
@RestControllerAdvice(basePackages = "com.plumliu.shorturl")
public class GlobalResponseAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        Class<?> clazz = returnType.getParameterType();
        // 只对非 ResponseEntity、非 StreamingResponseBody、非 Resource 的类型进行 Result 封装
        return !clazz.equals(ResponseEntity.class)
                && !clazz.getName().equals("org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody")
                && !org.springframework.core.io.Resource.class.isAssignableFrom(clazz);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

        HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();

        String requestId = Optional.ofNullable((String) servletRequest.getAttribute("X-Request-Id"))
                .orElseThrow(() -> new ClientException(BaseErrorCode.MISSING_REQUEST_ID));

        // 无返回值(返回类型为 void), 直接返回 Result.success() 即可
        if(body == null) {
            return Result.success(requestId);
        }

        // 由 GlobalExceptionHandler 捕获异常后返回的Result封装类, 业务的执行是失败的
        if (body instanceof Result<?>) {
            log.warn("异常类");
            return ((Result<?>) body).withRequestId(requestId);
        }

        // 正常的返回值
        Result<Object> result = Result.success(body, requestId);

        // 如果返回值正常且类型为 String , 需要额外的转换为 JSON 串
        if(body instanceof String) {
            return JSONUtil.toJsonStr(result);
        }

        return result;
    }
}
