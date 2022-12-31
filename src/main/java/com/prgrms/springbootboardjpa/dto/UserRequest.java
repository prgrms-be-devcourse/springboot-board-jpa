package com.prgrms.springbootboardjpa.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class UserRequest {
    @NotBlank(message = "이름은 공백일 수 없습니다.")
    private String name;

    @NotNull(message = "나이는 비어있을 수 없습니다.")
    @Positive(message = "나이는 음수일 수 없습니다.")
    private Integer age;

    @NotBlank
    private String hobby;
}
