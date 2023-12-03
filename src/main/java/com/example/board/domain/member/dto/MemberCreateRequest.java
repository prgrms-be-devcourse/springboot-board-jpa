package com.example.board.domain.member.dto;

import com.example.board.domain.member.entity.Member;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.password.PasswordEncoder;

public record MemberCreateRequest(
        @Email
        @NotBlank(message = "이메일은 필수 항목입니다.")
        String email,

        @NotBlank(message = "비밀번호는 필수 항목입니다.")
        @Length(min = 8, max = 15, message = "비밀번호는 최소 8자, 최대 15자로 설정할 수 있습니다.")
        @Pattern(
                regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=!]).*$",
                message = "비밀번호는 적어도 하나의 숫자, 하나의 문자, 그리고 하나의 특수문자를 포함해야 합니다."
        )
        String password,

        @Length(min = 2, max = 15, message = "이름은 최소 2자, 최대 15자로 설정할 수 있습니다.")
        String name,

        @Min(value = 1, message = "나이는 1이상의 정수만 입력 가능합니다.")
        int age,

        @NotBlank(message = "취미는 필수 항목입니다.")
        @Length(min = 2, max = 20, message = "취미는 최소 2자, 최대 20자로 설정할 수 있습니다.")
        String hobby
) {
        public Member toEntity(PasswordEncoder passwordEncoder) {
                return new Member(
                        email,
                        name,
                        passwordEncoder.encode(password),
                        age,
                        hobby
                );
        }
}
