package com.prgrms.board.post.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record PostDetailedRequest(
        @NotBlank
        @Size(min = 3, max = 100)
        String title,

        @NotBlank
        @Size(min = 10, max = 500)
        String content,

        @NotNull
        @Positive
        Long userId
) {
}
