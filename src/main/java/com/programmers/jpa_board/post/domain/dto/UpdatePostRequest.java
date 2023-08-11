package com.programmers.jpa_board.post.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdatePostRequest(
        @NotBlank
        @Size(max = 100, message = "최대 사이즈는 100자입니다.")
        String title,
        String content
) implements PostDto {
}
