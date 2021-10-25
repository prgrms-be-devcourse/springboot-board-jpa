package com.kdt.board.dto.post;

public class PostRequest {
    private String title;
    private String content;
    private String userName;

    public PostRequest(String title, String content, String userName) {
        this.title = title;
        this.content = content;
        this.userName = userName;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getTitle() {
        return this.title;
    }

    public String getContent() {
        return this.content;
    }
}
