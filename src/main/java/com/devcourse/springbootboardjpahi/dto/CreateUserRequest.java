package com.devcourse.springbootboardjpahi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

public record CreateUserRequest(
        @NotBlank
        String name,
        @PositiveOrZero
        Integer age,
        String hobby) {

}
