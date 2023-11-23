package com.devcourse.springbootboardjpahi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

public record CreateUserRequest(
        @NotBlank(message = "이름은 공백일 수 없습니다.")
        String name,
        @PositiveOrZero(message = "나이는 음수일 수 없습니다.")
        Integer age,
        String hobby
) {
}
