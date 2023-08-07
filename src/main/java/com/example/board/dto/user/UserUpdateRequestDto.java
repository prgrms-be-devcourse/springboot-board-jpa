package com.example.board.dto.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserUpdateRequestDto {
    private static final String NON_BLANK_PATTERN = "^(?!\\s*$).+";

    @Pattern(regexp = NON_BLANK_PATTERN, message = "공백은 허용되지 않습니다")
    private String name;

    @Email(message = "올바른 이메일 형식을 입력해주세요")
    private String email;

    @Size(min = 8, message = "비밀번호는 최소 8자리 이상이여야 합니다")
    private String password;

    @Min(value = 1, message = "나이는 최소 1부터 입력 가능합니다")
    private Integer age;

    private String hobby;
}
