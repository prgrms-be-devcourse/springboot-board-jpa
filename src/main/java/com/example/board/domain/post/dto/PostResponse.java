package com.example.board.domain.post.dto;

import com.example.board.domain.post.entity.Post;

public record PostResponse(
    Long id,
    String title,
    String content,
    int view,
    String name
) {
    public static PostResponse from(Post post) {
        return new PostResponse(
            post.getId(),
            post.getTitle(),
            post.getContent(),
            post.getView(),
            post.getMember().getName()
        );
    }
}
