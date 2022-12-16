package com.prgrms.jpa.controller.dto.post;

import lombok.Getter;

@Getter
public class UpdatePostRequest {
    private final String title;
    private final String content;

    public UpdatePostRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
