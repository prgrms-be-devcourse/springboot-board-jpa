package dev.jpaboard.post.dto;

import dev.jpaboard.post.domain.Post;

public record PostResponse(String title, String content) {

    public static PostResponse from(Post post) {
        return new PostResponse(post.getTitle(), post.getContent());
    }

}
