package com.programmers.jpa_board.post.domain.dto.request;

public class UpdatePostRequest {
    private String title;
    private String content;

    public UpdatePostRequest() {
    }

    public UpdatePostRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
