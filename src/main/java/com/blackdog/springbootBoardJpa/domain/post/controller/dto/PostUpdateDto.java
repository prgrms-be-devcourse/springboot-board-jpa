package com.blackdog.springbootBoardJpa.domain.post.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PostUpdateDto(
        @NotBlank
        String title,

        @NotNull
        @Size(max = 255)
        String content
) {
}
