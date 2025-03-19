package com.devcourse.springbootboardjpahi.dto;

import com.devcourse.springbootboardjpahi.message.PostExceptionMessage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdatePostRequest(
        @NotBlank(message = PostExceptionMessage.BLANK_TITLE)
        String title,
        @NotNull(message = PostExceptionMessage.NULL_CONTENT)
        String content
) {

}
