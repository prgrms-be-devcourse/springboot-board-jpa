package com.prgrms.hyuk.dto;

public class PostUpdateRequest {

    private String title;
    private String content;

    public PostUpdateRequest(String title, String content) {
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
