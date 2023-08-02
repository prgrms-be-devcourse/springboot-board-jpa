package com.programmers.jpa_board.post.domain.dto.request;

public class UpdatePostRequest {
    private String title;
    private String content;

    public UpdatePostRequest() {
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
