package com.devcourse.springjpaboard.application.post.controller.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record CreatePostRequest(
    @NotBlank String title,
    @NotBlank String content,
    @NotNull @Min(1) Long userId
) {

}
