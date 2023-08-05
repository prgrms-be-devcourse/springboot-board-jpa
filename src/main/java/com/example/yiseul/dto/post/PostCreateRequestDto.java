package com.example.yiseul.dto.post;

import jakarta.validation.constraints.NotBlank;

public record PostCreateRequestDto(
        @NotBlank
        Long memberId,

        @NotBlank
        String title,

        @NotBlank
        String content) {
}
