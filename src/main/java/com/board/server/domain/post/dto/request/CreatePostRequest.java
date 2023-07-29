package com.board.server.domain.post.dto.request;

public record CreatePostRequest(
        String title,
        String content
) {
}
