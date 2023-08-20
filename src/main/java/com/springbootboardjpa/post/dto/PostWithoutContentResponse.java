package com.springbootboardjpa.post.dto;

import com.springbootboardjpa.post.domain.Post;

public record PostWithoutContentResponse(Long id, Long memberId, String tittle) {

    public PostWithoutContentResponse(Post post) {
        this(post.getId(), post.getMember().getId(), post.getTitle());
    }
}
