package com.example.jpaboard.service.dto.post;

import com.example.jpaboard.domain.post.Post;
import java.time.LocalDateTime;

public class PostResponse {
    private final Long id;
    private final String title;
    private final String content;
    private final String writer;
    private final LocalDateTime createdAt;

    public PostResponse(Long id, String title, String content, String writer, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.createdAt = createdAt;
    }

    public static PostResponse from(Post post) {
        return new PostResponse(post.getId(), post.getTitle(), post.getContent(), post.getUser().getName(), post.getCreatedAt());
    }
}
