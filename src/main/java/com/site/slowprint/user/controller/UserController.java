package com.site.slowprint.user.controller;

import com.site.slowprint.user.dto.LoginRequest;
import com.site.slowprint.user.dto.SignupRequest;
import com.site.slowprint.user.dto.UserResponse;
import com.site.slowprint.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody @Validated SignupRequest signupRequest) {
        userService.signUp(signupRequest);
        return new ResponseEntity<>("회원가입 완료", HttpStatus.OK);
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Validated LoginRequest loginRequest) {
        UserResponse token = userService.login(loginRequest);
        return new ResponseEntity<>(token.getToken(), HttpStatus.OK);
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        userService.logout(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
