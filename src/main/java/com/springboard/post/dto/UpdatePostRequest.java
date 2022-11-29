package com.springboard.post.dto;

import javax.validation.constraints.NotNull;

public record UpdatePostRequest(
    @NotNull(message = "title 값이 필요합니다.")
    String title,
    @NotNull(message = "content 값이 필요합니다.")
    String content
) {}