package com.site.slowprint.user.service;

import com.site.slowprint.user.domain.User;
import com.site.slowprint.user.dto.LoginRequest;
import com.site.slowprint.user.dto.SignupRequest;
import com.site.slowprint.user.dto.UserResponse;
import com.site.slowprint.user.exception.AlreadyExistsEmailException;
import com.site.slowprint.user.exception.PasswordNotMatchException;
import com.site.slowprint.user.exception.UserNotFoundException;
import com.site.slowprint.user.repository.UserRepository;
import com.site.slowprint.user.security.JwtTokenProvider;

import com.site.slowprint.user.security.PasswordEncoder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    // 회원가입
    @Transactional
    public void signUp(SignupRequest signupRequest) {
        Optional<User> userOptional = userRepository.findByEmail(signupRequest.getEmail());
        // 이메일 중복 체크
        if (userOptional.isPresent()) {
            throw new AlreadyExistsEmailException();
        }
        // 비밀번호 일치 체크
        if (!Objects.equals(signupRequest.getPassword1(), signupRequest.getPassword2())) {
            throw new PasswordNotMatchException();
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(signupRequest.getEmail(), signupRequest.getPassword1());

        // 사용자 정보 저장
        User user = User.builder()
                .email(signupRequest.getEmail())
                .password(encodedPassword)
                .build();

        userRepository.save(user);

    }

    // 로그인
    @Transactional(readOnly = true)
    public UserResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(UserNotFoundException::new);

        // 비밀번호 비교
        if(!Objects.equals(loginRequest.getPassword(), user.getPassword())){
            throw new PasswordNotMatchException();
        }

        // JWT 토큰 생성
        String token = jwtTokenProvider.createToken(user.getEmail());
        user.setAuthToken(token);

        return UserResponse.builder()
                .email(user.getEmail())
                .totalMileage(user.getTotalMileage())
                .token(token)
                .build();

    }

    // 로그아웃
    public void logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }

    // 비밀번호 암호화
    public String encodePassword(String email,String password) {
        return passwordEncoder.encode(email, password);
    }

}
