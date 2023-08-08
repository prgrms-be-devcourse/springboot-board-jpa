package com.blackdog.springbootBoardJpa.domain.user.controller.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record UserCreateDto(
        @NotBlank
        String name,

        @Min(0)
        int age,

        @NotBlank
        String hobby
) {
}
