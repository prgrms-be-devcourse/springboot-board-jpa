package com.waterfogsw.springbootboardjpa.post.controller.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public record PostUpdateRequest(
        @Length(max = 100, message = "Title should not be more then 100 characters long")
        @NotBlank(message = "Title should not be blank")
        String title,

        @NotBlank(message = "content should not be blank")
        String content,

        @NotNull(message = "User Id should not be null")
        @Positive(message = "User Id should be positive number")
        long userId
) {
}
