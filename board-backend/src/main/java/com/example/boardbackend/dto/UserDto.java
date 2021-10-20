package com.example.boardbackend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
    private Long id;

    @Email(message = "이메일 형식이 맞지 않습니다")
    @NotBlank(message = "이메일을 입력해주세요")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요")
    @Size(message="비밀번호는 최대 45자까지 입력 가능합니다", max=45)
    private String password;

    @NotBlank(message = "이름을 입력해주세요")
    @Size(message="이름은 최대 30자까지 입력 가능합니다", max=30)
    @Pattern(message="이름은 한글 및 영어만 입력 가능합니다", regexp = "^[가-힣a-zA-Z]*$")
    private String name;

    @Positive(message = "나이는 1세 이상이어야합니다")
    @Digits(message = "나이는 99세까지만 입력 가능합니다", integer=99, fraction = 0)
    private int age;

    @NotBlank(message = "취미를 입력해주세요")
    @Size(message="취미는 최대 45자까지 입력 가능합니다", max=45)
    private String hobby;

    private LocalDateTime createdAt;
}
