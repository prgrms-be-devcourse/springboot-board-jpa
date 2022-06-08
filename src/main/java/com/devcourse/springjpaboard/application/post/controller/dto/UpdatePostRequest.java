package com.devcourse.springjpaboard.application.post.controller.dto;

import static com.devcourse.springjpaboard.core.exception.ExceptionMessage.BLANK_CONTENT;
import static com.devcourse.springjpaboard.core.exception.ExceptionMessage.BLANK_TITLE;

import javax.validation.constraints.NotBlank;

public record UpdatePostRequest(
    @NotBlank(message = BLANK_TITLE) String title,
    @NotBlank(message = BLANK_CONTENT) String content
) {

}
