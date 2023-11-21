package com.example.board.domain.post.dto;

import com.example.board.domain.post.entity.Post;
import java.time.format.DateTimeFormatter;

public record PostResponse(
    Long id,
    String title,
    String content,
    int view,
    String name,
    String createdAt,
    String updatedAt
) {
    public static PostResponse from(Post post) {
        return new PostResponse(
            post.getId(),
            post.getTitle(),
            post.getContent(),
            post.getView(),
            post.getMember().getName(),
            post.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
            post.getUpdatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        );
    }
}
