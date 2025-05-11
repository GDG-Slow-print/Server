package com.site.slowprint.user.security;

import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    public String createToken(String username) {
        // JWT 토큰 생성 로직
        return "generated-jwt-token";
    }

    public boolean validateToken(String token) {
        // JWT 토큰 검증 로직
        return true;
    }
}
