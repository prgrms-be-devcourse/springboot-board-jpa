package com.kdt.devboard.post.Dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Builder
public class PostUpdateRequest {

    @NotNull
    private final Long postId;

    @NotBlank
    private final String title;

    @NotBlank
    private final String content;

}
