package com.programmers.springbootboard.post.dto.response;

import lombok.Builder;
import lombok.Data;

@Builder
public class PostInsertResponse {
    private final Long id;
    private final String title;
    private final String content;

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
