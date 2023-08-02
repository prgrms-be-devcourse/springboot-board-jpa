package com.example.springbootjpa.ui.dto.post;

public record PostUpdateRequest(
        String title,
        String content
) {
}
