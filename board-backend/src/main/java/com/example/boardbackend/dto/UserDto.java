package com.example.boardbackend.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Builder
public class UserDto {
    private Long id;

    @NotBlank(message = "이메일을 입력해주세요")
    @Email(message = "이메일 형식이 맞지 않습니다")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요")
    private String password;

    @NotBlank(message = "이름을 입력해주세요")
    private String name;

    @NotNull(message = "나이를 입력해주세요")
    private int age;

    @NotBlank(message = "취미를 입력해주세요")
    private String hobby;

    private LocalDateTime createdAt;
}
