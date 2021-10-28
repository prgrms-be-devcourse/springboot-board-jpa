package com.programmers.springbootboard.post.dto.response;

import lombok.Builder;

@Builder
public class PostDeleteResponse {
    private final Long postId;
    private final String email;

    public Long getPostId() {
        return postId;
    }

    public String getEmail() {
        return email;
    }
}
