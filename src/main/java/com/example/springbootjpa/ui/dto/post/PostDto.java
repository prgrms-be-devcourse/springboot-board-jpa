package com.example.springbootjpa.ui.dto.post;

import com.example.springbootjpa.domain.post.Post;

public record PostDto(
        long postId,
        String title,
        String content,
        long userId) {

    public static PostDto from(Post post) {
        return new PostDto(post.getId(), post.getTitle(), post.getContent(), post.getUser().getId());
    }
}
