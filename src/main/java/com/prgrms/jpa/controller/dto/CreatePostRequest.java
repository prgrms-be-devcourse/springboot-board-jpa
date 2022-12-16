package com.prgrms.jpa.controller.dto;

import lombok.Getter;

@Getter
public class CreatePostRequest {
    private final String title;
    private final String content;
    private final Long userId;

    public CreatePostRequest(String title, String content, Long userId) {
        this.title = title;
        this.content = content;
        this.userId = userId;
    }
}
