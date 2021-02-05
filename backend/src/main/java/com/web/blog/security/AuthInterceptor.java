package com.web.blog.security;

import com.web.blog.exception.CustomAuthenticationException;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {
    // @RequiredArgsConstructor 를 통한 final 필드의 DI(의존성 주입)
    private final JwtAuthTokenProvider jwtAuthTokenProvider;
    // front-end 에서 Header에 실어보내는 token의 key
    private static final String AUTHORIZATION_HEADER = "x-auth-token";

    public boolean preHandle(HttpServletRequest servletRequest, HttpServletResponse servletResponse,
                             Object handler) throws Exception {
        log.info("preHandle!!");

        Optional<String> token = resolveToken(servletRequest);

        if (token.isPresent()) {
            JwtAuthToken jwtAuthToken = jwtAuthTokenProvider.convertAuthToken(token.get());
            // Role은 아직 사용하지 않음
            if(jwtAuthToken.validate()/* & Role.USER.getCode().equals(jwtAuthToken.getData().get("role"))*/) {
                return true;
            }
            else {
                throw new CustomAuthenticationException();
            }
        } else {
            throw new CustomAuthenticationException();
        }
    }

    // 헤더에 있는 "x-auth-token" 에 저장된 token을 가져옴
    // null의 처리 떄문에 Optional을 사용
    private Optional<String> resolveToken(HttpServletRequest request) {
        String authToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(authToken)) {
            // 토큰은 반드시 값이 있어야 하기 때문에 Optional.of()를 사용
            return Optional.of(authToken);
        } else {
            return Optional.empty();
        }
    }
}
