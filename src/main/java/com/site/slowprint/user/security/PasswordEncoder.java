package com.site.slowprint.user.security;

public class PasswordEncoder {
    public String encode(String rawPassword) {
        // 비밀번호 암호화 로직
        return "encoded-password";
    }

    public boolean matches(String rawPassword, String encodedPassword) {
        // 비밀번호 비교 로직
        return rawPassword.equals(encodedPassword);
    }
}
