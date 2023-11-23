package com.programmers.boardjpa.post.dto;

import lombok.Builder;

import java.time.LocalDateTime;

public record PostResponseDto (Long postId, String title, String content, LocalDateTime createdAt, LocalDateTime updatedAt, Long userId) {

    @Builder
    public PostResponseDto(Long postId, String title, String content, LocalDateTime createdAt, LocalDateTime updatedAt, Long userId) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.userId = userId;
    }
}
