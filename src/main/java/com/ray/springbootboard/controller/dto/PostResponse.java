package com.ray.springbootboard.controller.dto;

import com.ray.springbootboard.domain.Post;

public record PostResponse(Long id, String title, String content, String createdBy) {

    public PostResponse(Post post) {
        this(post.getId(), post.getTitle(), post.getContent(), post.getCreatedBy());
    }
}
