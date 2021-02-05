package com.web.blog.service.user;

import com.web.blog.model.user.User;
import com.web.blog.security.AuthToken;
import com.web.blog.security.JwtAuthTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final JwtAuthTokenProvider jwtAuthTokenProvider;
    private final static long LOGIN_RETENTION_MINUTES = 30;

    //  user의 email로 토큰생성 및 만료시간 설정
    public AuthToken createAuthToken(User user) {
        Date expiredDate = Date.from(LocalDateTime.now().plusMinutes(LOGIN_RETENTION_MINUTES)
                .atZone(ZoneId.systemDefault()).toInstant());
        return jwtAuthTokenProvider.createAuthToken(user.getEmail(), expiredDate);
    }
}
