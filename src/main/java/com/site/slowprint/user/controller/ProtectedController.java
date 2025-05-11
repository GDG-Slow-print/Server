package com.site.slowprint.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ProtectedController {

    @GetMapping("/protected")
    public ResponseEntity<String> checkToken(Authentication authentication) {
        // 인증된 사용자 정보 확인
        String email = authentication.getName();
        return ResponseEntity.ok("🔐 인증된 사용자: " + email);
    }
}
