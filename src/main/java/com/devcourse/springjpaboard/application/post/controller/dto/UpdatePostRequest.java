package com.devcourse.springjpaboard.application.post.controller.dto;

import javax.validation.constraints.NotBlank;

public record UpdatePostRequest(
    @NotBlank String title,
    @NotBlank String content
) {

}
