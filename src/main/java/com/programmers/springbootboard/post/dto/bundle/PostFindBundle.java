package com.programmers.springbootboard.post.dto.bundle;

import lombok.Builder;

@Builder
public class PostFindBundle {
    private final Long postId;

    public Long getPostId() {
        return postId;
    }
}
