package com.example.board.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @Email(message = "이메일은 필수 입력값입니다.")
        String email,

        @NotBlank(message = "비밀번호는 필수 입력값입니다.")
        String password
) {
}
