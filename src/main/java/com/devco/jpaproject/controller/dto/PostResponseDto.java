package com.devco.jpaproject.controller.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Builder
@AllArgsConstructor
public class PostResponseDto {
    @NotNull(message = "writerDto should not be null")
    private UserResponseDto writerDto;

    @NotNull(message = "post id should not be null")
    private Long postId;

    @NotBlank(message = "title should not be null")
    private String title;

    @NotBlank(message = "content should not be null")
    private String content;
}
