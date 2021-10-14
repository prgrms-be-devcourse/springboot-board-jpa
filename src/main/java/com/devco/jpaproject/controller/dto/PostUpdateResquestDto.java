package com.devco.jpaproject.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Builder
@AllArgsConstructor
public class PostUpdateResquestDto {
    @NotNull(message = "writerDto should not be null")
    private UserResponseDto writerDto;

    @NotNull(message = "post id should not be null")
    private Long postId;

    @NotBlank(message = "title should not be null")
    private String title;

    @NotBlank(message = "content should not be null")
    private String content;
}
