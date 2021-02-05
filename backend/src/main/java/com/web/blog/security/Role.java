package com.web.blog.security;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Role {
    // 아직 사용하지 않는 enum
    USER("ROLE_USER", "사용자권한"),
    UNKNOWN("UNKNOWN", "알수없는 권한");

    private String code;
    private String description;

    Role(String code, String description){
        this.code = code;
        this.description = description;
    }

    public static Role of(String code) {
        return Arrays.stream(Role.values())
                .filter(r -> r.getCode().equals(code))
                .findAny()
                .orElse(UNKNOWN);
    }
}
