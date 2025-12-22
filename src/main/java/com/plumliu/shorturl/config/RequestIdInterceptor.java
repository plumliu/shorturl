package com.plumliu.shorturl.config;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;


public class RequestIdInterceptor implements HandlerInterceptor {

    private static final String REQUEST_ID_KEY = "X-Request-Id";


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        String requestId = request.getHeader(REQUEST_ID_KEY);

        if (StrUtil.isBlank(requestId)) {
            requestId = IdUtil.fastSimpleUUID();
        }

        MDC.put(REQUEST_ID_KEY, requestId);

        request.setAttribute(REQUEST_ID_KEY, requestId);

        response.setHeader(REQUEST_ID_KEY, requestId);

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        MDC.remove(REQUEST_ID_KEY);
        MDC.clear();
    }

}
