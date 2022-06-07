package com.devcourse.springjpaboard.application.user.controller.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public record CreateUserRequest(
    @NotBlank String name,
    @Positive @Max(200) int age,
    @NotBlank String hobby
) {

}
