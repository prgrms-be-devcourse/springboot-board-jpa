package com.devcourse.springjpaboard.application.post.controller.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public record CreatePostRequest(
    @NotBlank String title,
    @NotBlank String content,
    @NotNull @Positive Long userId
) {

}
