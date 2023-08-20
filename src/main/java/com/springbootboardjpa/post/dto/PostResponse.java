package com.springbootboardjpa.post.dto;

import com.springbootboardjpa.post.domain.Post;

public record PostResponse(Long id, Long memberId, String content, String title) {

    public PostResponse(Post post) {
        this(post.getId(), post.getMember().getId(), post.getContent().getContent(), post.getTitle());
    }
}
