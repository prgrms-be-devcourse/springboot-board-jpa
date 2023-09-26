package com.example.yiseul.dto.post;

public record PostResponseDto(
    Long postId,
    String title,
    String content,
    String createdAt,
    String createdBy
) {
}
