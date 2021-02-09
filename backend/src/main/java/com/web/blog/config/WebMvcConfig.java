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

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        System.out.println(" 인터셉터 작동! ");
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/api-sessions/**")    // token 인증을 거치는 Path
                .excludePathPatterns("/account/login");    // 제외 Path
    }
}
