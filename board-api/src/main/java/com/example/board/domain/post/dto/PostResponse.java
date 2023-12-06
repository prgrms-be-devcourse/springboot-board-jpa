package com.example.board.domain.post.dto;

import com.example.board.domain.post.entity.Post;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record PostResponse(
    Long id,
    String title,
    String content,
    int view,
    String name,
    String createdAt,
    String updatedAt,
    String updatedBy
) {

    public static final DateTimeFormatter FORMATTER_YYYY_MM_DD = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static PostResponse from(Post post) {
        return new PostResponse(
            post.getId(),
            post.getTitle(),
            post.getContent(),
            post.getView(),
            post.getMember().getIsDeleted() ? "(알 수 없음)" : post.getMember().getName(),
            post.getCreatedAt() != null ? post.getCreatedAt().format(FORMATTER_YYYY_MM_DD) : LocalDateTime.now().format(FORMATTER_YYYY_MM_DD),
            post.getUpdatedAt() != null ? post.getUpdatedAt().format(FORMATTER_YYYY_MM_DD) : LocalDateTime.now().format(FORMATTER_YYYY_MM_DD),
            post.getMember().getIsDeleted() ? "(알 수 없음)" : post.getUpdatedBy()
        );
    }
}
