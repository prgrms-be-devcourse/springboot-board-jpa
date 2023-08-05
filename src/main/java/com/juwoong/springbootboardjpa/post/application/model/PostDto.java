package com.juwoong.springbootboardjpa.post.application.model;

import java.time.LocalDateTime;

import com.juwoong.springbootboardjpa.post.domain.Post;

public record PostDto(Long id, Long userId, String title, String content, LocalDateTime createdAt,
                      LocalDateTime updatedAt) {

    public PostDto(Post post) {
        this(post.getId(), post.getUser().getId(), post.getTitle(), post.getContent(), post.getCreatedAt(),
            post.getUpdatedAt());
    }
}
