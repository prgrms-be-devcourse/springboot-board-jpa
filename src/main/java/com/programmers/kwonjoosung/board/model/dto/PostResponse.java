package com.programmers.kwonjoosung.board.model.dto;

import com.programmers.kwonjoosung.board.model.Post;

public record PostResponse(

        String title,

        String content,

        String userName,

        String createdAt
) {
    public PostResponse(Post post){
        this(post.getTitle(),
            post.getContent(),
            post.getUser().getName(),
            post.getCreatedAt().toString());
    }
}
