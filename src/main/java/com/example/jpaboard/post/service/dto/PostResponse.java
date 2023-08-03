package com.example.jpaboard.post.service.dto;

import com.example.jpaboard.post.domain.Post;

public record PostResponse(String title, String content, String memberName) {
    public PostResponse(Post post) {
        this(post.getTitle(), post.getContent(), post.getMember().getName());
    }
}
