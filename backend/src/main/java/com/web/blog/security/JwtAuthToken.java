package com.web.blog.security;

import com.web.blog.exception.CustomJwtRuntimeException;
import io.jsonwebtoken.*;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import lombok.var;

import java.security.Key;
import java.util.Date;
import java.util.Optional;

@Slf4j
public class JwtAuthToken implements AuthToken<Claims> {
    @Getter
    private final String token;
    private final Key key;

    // role으로 권한을 체크 (아직 사용하지 않음)
    private static final String AUTHORITIES_KEY = "role";

    JwtAuthToken(String token, Key key){
        this.token = token;
        this.key = key;
    }

    JwtAuthToken(String id, Date expiredDate, Key key){
        this.key = key;
        this.token = createJwtAuthToken(id, expiredDate).get();
    }

    private Optional<String> createJwtAuthToken(String id, Date expiredDate) {
        var token = Jwts.builder()
                .setSubject(id)
                .signWith(key, SignatureAlgorithm.HS256)
                .setExpiration(expiredDate)
                .compact();

        return Optional.ofNullable(token);
    }

    public boolean validate() {
        return getData() != null;
    }

    public Claims getData(){
        try {
            // Jwt 의 Body를 가져옴
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        } catch (SecurityException e){
            log.info("Invalid JWT signature.");
            throw new CustomJwtRuntimeException(e);
        } catch (MalformedJwtException e) {
            log.info("Invalid JWT token.");
            throw new CustomJwtRuntimeException(e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token.");
            throw new CustomJwtRuntimeException(e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token.");
            throw new CustomJwtRuntimeException(e);
        } catch (IllegalArgumentException e) {
            log.info("JWT token compact of handler are invalid.");
            throw new CustomJwtRuntimeException(e);
        }
    }

}
