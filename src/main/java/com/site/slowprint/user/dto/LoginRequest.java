package com.site.slowprint.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    @NotEmpty(message = "이메일은 필수 입력값입니다.")
    @Email(message = "이메일 형식이 아닙니다.")
    private String email;

    @NotEmpty(message = "비밀번호는 필수 입력값입니다.")
    private String password;

}
