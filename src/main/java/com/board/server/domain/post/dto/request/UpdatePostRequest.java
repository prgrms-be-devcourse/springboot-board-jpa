package com.board.server.domain.post.dto.request;

public record UpdatePostRequest(
        String title,
        String content
) {
}
