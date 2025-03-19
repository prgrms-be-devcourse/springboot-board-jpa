package com.devcourse.springbootboardjpahi.dto;

import com.devcourse.springbootboardjpahi.message.UserExceptionMessage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

public record CreateUserRequest(
        @NotBlank(message = UserExceptionMessage.BLANK_NAME)
        String name,
        @PositiveOrZero(message = UserExceptionMessage.NEGATIVE_AGE)
        Integer age,
        String hobby
) {

}
