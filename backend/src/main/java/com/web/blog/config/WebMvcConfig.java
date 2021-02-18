package com.web.blog.config;

import com.web.blog.security.AuthInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {
    private final AuthInterceptor authInterceptor;

    // 인터셉터 추가
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/api/api-sessions/**", "/api/account/update", "/api/account/delete")    // token 인증을 거치는 Path
                .excludePathPatterns("/api/account/login");    // 제외 Path
    }
}
