package com.example.springbootjpa.ui.dto.post;

public record PostSaveRequestDto(
        long userId,
        String title,
        String content
) {
}
