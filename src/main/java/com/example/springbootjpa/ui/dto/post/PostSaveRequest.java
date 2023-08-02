package com.example.springbootjpa.ui.dto.post;

public record PostSaveRequest(
        long userId,
        String title,
        String content
) {
}
