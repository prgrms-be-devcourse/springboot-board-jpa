package com.devcourse.springjpaboard.application.user.controller.dto;

import static com.devcourse.springjpaboard.core.exception.ExceptionMessage.BLANK_HOBBY;
import static com.devcourse.springjpaboard.core.exception.ExceptionMessage.BLANK_NAME;
import static com.devcourse.springjpaboard.core.exception.ExceptionMessage.INVALID_RANGE_AGE;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

public record CreateUserRequest(
    @NotBlank(message = BLANK_NAME) String name,
    @PositiveOrZero(message = INVALID_RANGE_AGE) @Max(value = 200, message = INVALID_RANGE_AGE) int age,
    @NotBlank(message = BLANK_HOBBY) String hobby
) {

}
