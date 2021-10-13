package com.devco.jpaproject.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@Builder
@AllArgsConstructor
public class PostDeleteRequestDto {
    @NotNull(message = "post id should not be null")
    private Long postId;

    @NotNull(message = "writer id should not be null")
    private Long writerId;
}
