package com.programmers.board.dto.request;

import lombok.Getter;

@Getter
public class PostCreateRequest {
    private final Long userId;
    private final String title;
    private final String content;

    public PostCreateRequest(Long userId, String title, String content) {
        this.userId = userId;
        this.title = title;
        this.content = content;
    }
}
