package com.programmers.springbootboard.post.dto.response;

import lombok.Builder;

@Builder
public class PostInsertResponse {
    private final Long postId;
    private final String title;
    private final String content;

    public Long getPostId() {
        return postId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
