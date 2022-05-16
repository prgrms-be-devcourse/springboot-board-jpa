package com.prgrms.springboard.post.dto;

import com.prgrms.springboard.post.domain.Post;
import com.prgrms.springboard.user.dto.UserResponse;

import lombok.Getter;

@Getter
public class PostResponse {

    private Long id;
    private String title;
    private String content;
    private UserResponse userResponse;

    protected PostResponse() {
    }

    public PostResponse(Long id, String title, String content, UserResponse userResponse) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.userResponse = userResponse;
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
