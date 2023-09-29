package com.blackdog.springbootBoardJpa.domain.post.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PostUpdateRequest(
        @NotBlank
        String title,

        @NotNull
        @Size(max = 255)
        String content
) {
}
