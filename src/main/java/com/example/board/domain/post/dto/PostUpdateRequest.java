package com.example.board.domain.post.dto;

public record PostUpdateRequest(
    String title,
    String content
) {
}
