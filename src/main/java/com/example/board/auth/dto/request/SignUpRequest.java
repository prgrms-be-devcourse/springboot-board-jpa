package com.example.board.auth.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.*;

public record SignUpRequest(
        @Email(message = "이메일은 필수 입력값입니다.")
        String email,

        @NotBlank(message = "비밀번호는 필수 입력값입니다.")
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9]).{8,20}$", message = "비밀번호는 영문자와 숫자를 포함한 8 ~ 20자로 입력해 주세요.")
        String password,

        @NotBlank(message = "비밀번호 확인은 필수 입력값입니다.")
        String passwordConfirm,

        @NotBlank(message = "이름은 필수 입력값입니다.")
        @Size(min = 2, max = 10, message = "이름은 2 ~ 10자 사이로 입력해 주세요.")
        String name,

        @NotNull(message = "나이는 필수 입력값입니다.")
        @Min(value = 1, message = "나이는 1살 이상으로 입력해 주세요.")
        @Max(value = 100, message = "나이는 100살 이하로 입력해 주세요.")
        Integer age
) {
    @JsonIgnore
    public boolean isPasswordEqualsToPasswordConfirm() {
        return password.equals(passwordConfirm);
    }
}
