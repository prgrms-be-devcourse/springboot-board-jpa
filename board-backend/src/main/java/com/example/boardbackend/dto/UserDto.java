package com.example.boardbackend.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Getter
@Builder
public class UserDto {
    private Long id;

    @Email(message = "이메일 형식이 맞지 않습니다")
    @NotBlank(message = "이메일을 입력해주세요")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요")
    private String password;

    @NotBlank(message = "이름을 입력해주세요")
    private String name;

    @Positive(message = "나이는 1이상이어야합니다.")
    private int age;

    @NotBlank(message = "취미를 입력해주세요")
    private String hobby;

    private LocalDateTime createdAt;
}
