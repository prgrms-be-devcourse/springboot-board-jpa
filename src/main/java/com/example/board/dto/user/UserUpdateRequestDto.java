package com.example.board.dto.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserUpdateRequestDto {
    @NotBlank(message = "이름에는 공백을 사용할 수 없습니다")
    private String name;

    @NotBlank(message = "이메일에는 공백을 사용할 수 없습니다")
    @Email(message = "올바른 이메일 형식을 입력해주세요")
    private String email;

    @NotBlank(message = "비밀번호에는 공백을 사용할 수 없습니다")
    @Size(min = 8, message = "비밀번호는 최소 8자리 이상이여야 합니다")
    private String password;

    @Min(value = 1, message = "나이는 최소 1부터 입력 가능합니다")
    private Integer age;

    private String hobby;
}
