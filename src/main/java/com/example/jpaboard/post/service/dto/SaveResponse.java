package com.example.jpaboard.post.service.dto;

import com.example.jpaboard.post.domain.Post;

public record SaveResponse(Long postId, String title, String content) {
    public SaveResponse(Post post) {
        this(post.getId(), post.getTitle(), post.getTitle());
    }
}
