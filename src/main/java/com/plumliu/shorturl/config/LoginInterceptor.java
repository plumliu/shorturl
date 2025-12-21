package com.plumliu.shorturl.config;

import com.plumliu.shorturl.common.biz.user.UserContext;
import com.plumliu.shorturl.common.convention.errorcode.BaseErrorCode;
import com.plumliu.shorturl.common.convention.exception.ClientException;
import com.plumliu.shorturl.domain.dto.UserLoginReqDTO;
import com.plumliu.shorturl.domain.dto.UserLoginRespDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
public class LoginInterceptor implements HandlerInterceptor {

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new ClientException(BaseErrorCode.UNAUTHORIZED);
        }

        String token = authHeader.substring(7).trim();

        if (token.isEmpty()) {
            throw new ClientException(BaseErrorCode.UNAUTHORIZED);
        }

        String key = "login:token:" + token;
        Object value = Optional.ofNullable(redisTemplate.opsForValue().get(key))
                .orElseThrow(() -> new ClientException(BaseErrorCode.INVALID_TOKEN));

        UserLoginRespDTO userLoginRespDTO = (UserLoginRespDTO) value;
        redisTemplate.expire(key, 30, TimeUnit.MINUTES);
        UserContext.setUser(userLoginRespDTO);

        return true;
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserContext.removeUser();
    }

}
