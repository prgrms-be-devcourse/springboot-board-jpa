package com.example.springbootjpa.ui.dto.post;

import com.example.springbootjpa.domain.post.Post;

public record PostFindResponse(
        long postId,
        String title,
        String content,
        long userId) {

    public static PostFindResponse from(Post post) {
        return new PostFindResponse(post.getId(), post.getTitle(), post.getContent(), post.getUser().getId());
    }
}
