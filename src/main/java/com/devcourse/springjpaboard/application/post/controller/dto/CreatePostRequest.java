package com.devcourse.springjpaboard.application.post.controller.dto;

import static com.devcourse.springjpaboard.core.exception.ExceptionMessage.BLANK_CONTENT;
import static com.devcourse.springjpaboard.core.exception.ExceptionMessage.BLANK_TITLE;
import static com.devcourse.springjpaboard.core.exception.ExceptionMessage.NOT_VALID_USER_ID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public record CreatePostRequest(
    @NotBlank(message = BLANK_TITLE) String title,
    @NotBlank(message = BLANK_CONTENT) String content,
    @NotNull(message = NOT_VALID_USER_ID) @Positive(message = NOT_VALID_USER_ID) Long userId
) {

}
