package com.devcourse.springbootboardjpahi.dto;

import com.devcourse.springbootboardjpahi.message.PostExceptionMessage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreatePostRequest(
        @NotBlank(message = PostExceptionMessage.BLANK_TITLE)
        String title,
        @NotNull(message = PostExceptionMessage.NULL_CONTENT)
        String content,
        @Positive(message = PostExceptionMessage.INVALID_USER_ID)
        Long userId
) {

}
