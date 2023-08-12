package com.example.jpaboard.post.service.dto;

import com.example.jpaboard.member.domain.Name;
import com.example.jpaboard.post.domain.Post;

public record PostResponse(Long postId,
                           String title,
                           String content,
                           Name memberName) {

    public PostResponse(Post post) {
        this(post.getId(), post.getTitle(), post.getContent(), post.getMember().getName());
    }

}
