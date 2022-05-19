package com.prgrms.springboard.post.dto;

import com.prgrms.springboard.post.domain.Post;
import com.prgrms.springboard.user.dto.UserResponse;

import lombok.Getter;

@Getter
public class PostResponse {

    private final Long id;
    private final String title;
    private final String content;
    private final UserResponse user;

    public PostResponse(Long id, String title, String content, UserResponse user) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.user = user;
    }

    public static PostResponse from(Post post) {
        return new PostResponse(
            post.getId(),
            post.getTitle().getTitle(),
            post.getContent().getContent(),
            UserResponse.from(post.getUser())
        );
    }
}
