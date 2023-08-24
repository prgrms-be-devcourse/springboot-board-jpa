package com.example.yiseul.dto.post;

import jakarta.validation.constraints.Min;

public record PostUpdateRequestDto(
    @Min(value = 1)
    String title,

    @Min(value = 1)
    String content
) {
}
