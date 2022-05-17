package com.springboard.post.dto;

import java.time.LocalDateTime;

public record FindPostResponse(
    Long id,
    String title,
    String content,
    UserResponse user,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
    public record UserResponse(
        Long id,
        String name
    ) {}
}