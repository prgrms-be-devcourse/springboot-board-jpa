package com.example.boardbackend.dto.request;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Builder
@Getter
public class LoginRequest {
    @NotBlank(message = "이메일을 입력해주세요")
    @Email(message = "이메일 형식이 맞지 않습니다")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요")
    private String password;
}
