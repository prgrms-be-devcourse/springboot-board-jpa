package com.board.server.domain.post.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PostResponse(
        Long postId,
        String title,
        String content,
        String createdBy,
        LocalDateTime createdAt
) {
    public static PostResponse of(Long postId, String title, String content, String createdBy, LocalDateTime createdAt) {
        return PostResponse.builder()
                .postId(postId)
                .title(title)
                .content(content)
                .createdBy(createdBy)
                .createdAt(createdAt)
                .build();
    }
}
