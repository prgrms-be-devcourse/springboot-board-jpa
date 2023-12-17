package com.example.board.domain.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public record MemberCreateRequest(
        @Email
        @NotBlank(message = "이메일은 필수 항목입니다.")
        String email,

        @NotBlank(message = "비밀번호를 입력해주세요.")
        @Pattern(
                regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$",
                message = "비밀번호는 최소 8자리이면서 1개 이상의 알파벳, 숫자, 특수문자를 포함해야합니다.")
        String password,

        @Length(min = 2, max = 15, message = "이름은 최소 2자, 최대 15자로 설정할 수 있습니다.")
        String name,

        @Min(value = 1, message = "나이는 1이상의 정수만 입력 가능합니다.")
        int age,

        @NotBlank(message = "취미는 필수 항목입니다.")
        @Length(min = 2, max = 20, message = "취미는 최소 2자, 최대 20자로 설정할 수 있습니다.")
        String hobby,

        @NotBlank(message = "인증번호를 입력해주세요.")
        String authKey
) {
}
