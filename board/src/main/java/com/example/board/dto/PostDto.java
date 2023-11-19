package com.example.board.dto;

import com.example.board.domain.Post;
import lombok.Builder;

import java.time.LocalDateTime;

public class PostDto {
    @Builder
    public record Request(Long userId, String title, String content) {}

    @Builder
    public record Response(Long userId, String title, String content, LocalDateTime createdAt) {}

    public static Response toResponse(Post post) {
        return Response.builder()
                .userId(post.getUser().getId())
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .build();
    }
}
