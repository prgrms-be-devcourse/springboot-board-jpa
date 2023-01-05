package com.spring.board.springboard.user.domain.dto;

import jakarta.validation.constraints.NotBlank;

public record MemberLoginDto(
        @NotBlank(message = "이메일을 반드시 입력해주세요.") String email,
        @NotBlank(message = "비밀번호를 반드시 입력해주세요.") String password) {
}
