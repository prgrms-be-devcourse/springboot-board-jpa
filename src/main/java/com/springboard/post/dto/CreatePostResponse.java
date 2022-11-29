package com.springboard.post.dto;

import java.time.LocalDateTime;

public record CreatePostResponse(
    Long id,
    String title,
    String content,
    UserResponse user,
    LocalDateTime createdAt
) {
    public record UserResponse(
        Long id,
        String name
    ) {}
}