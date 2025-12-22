package com.plumliu.shorturl.config;


import com.plumliu.shorturl.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class UserConfig implements WebMvcConfigurer {

    private final RedisTemplate<String, Object> redisTemplate;

    private final List<String> excludes = Arrays.asList(
            "/api/user/login",
            "/api/user/register",
            "/api/user/has-username",
            "/api/user/has-phone",
            "/api/user/has-mail",
            "/doc.html",
            "/webjars/**",
            "/v3/api-docs/**",
            "/favicon.ico",
            "/s/**"
    );

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RequestIdInterceptor())
                .addPathPatterns("/**");

        registry.addInterceptor(new LoginInterceptor(redisTemplate))
                .addPathPatterns("/**")
                .excludePathPatterns(excludes);
    }

}
