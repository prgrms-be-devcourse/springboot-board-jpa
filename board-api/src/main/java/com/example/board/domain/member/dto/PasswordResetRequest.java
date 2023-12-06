package com.example.board.domain.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record PasswordResetRequest(
    @Email
    @NotBlank(message = "이메일은 필수 항목입니다.")
    String email,

    @NotBlank(message = "인증번호를 입력해주세요.")
    String authKey,

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$",
    message = "비밀번호는 최소 8자리이면서 1개 이상의 알파벳, 숫자, 특수문자를 포함해야합니다.")
    @NotBlank(message = "재설정할 비밀번호를 입력해주세요.")
    String password,

    @NotBlank(message = "재설정할 비밀번호를 다시 한 번 입력해주세요.")
     String verifyPassword
){
}
