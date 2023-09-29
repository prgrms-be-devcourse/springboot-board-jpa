package com.blackdog.springbootBoardJpa.domain.user.controller.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record UserCreateDto(
        @NotBlank(message = "이름은 공백일 수 없습니다.")
        String name,

        @Min(value = 0, message = "나이는 최소 0세 이상이어야 합니다")
        int age,

        @NotBlank(message = "이름은 공백일 수 없습니다.")
        String hobby
) {
}
