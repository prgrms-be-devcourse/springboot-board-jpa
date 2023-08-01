package com.programmers.springbootboardjpa.dto.post;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostCreateRequest {

    private final String title;
    private final String content;
    private final Long userId;

    @Builder
    public PostCreateRequest(String title, String content, Long userId) {
        this.title = title;
        this.content = content;
        this.userId = userId;
    }

}
