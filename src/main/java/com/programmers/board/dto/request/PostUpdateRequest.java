package com.programmers.board.dto.request;

import lombok.Getter;

@Getter
public class PostUpdateRequest {
    private final String title;
    private final String content;

    public PostUpdateRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
