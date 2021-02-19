package com.web.blog.security;

import io.jsonwebtoken.security.Keys;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

import java.security.Key;
import java.util.Date;

@Slf4j
public class JwtAuthTokenProvider implements AuthTokenProvider<JwtAuthToken> {
    private final Key key;

    public JwtAuthTokenProvider(String secret){
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public JwtAuthToken createAuthToken(String id, Date expiredDate){
        return new JwtAuthToken(id, expiredDate, key);
    }

    public JwtAuthToken convertAuthToken(String token){
        return new JwtAuthToken(token, key);
    }
}
