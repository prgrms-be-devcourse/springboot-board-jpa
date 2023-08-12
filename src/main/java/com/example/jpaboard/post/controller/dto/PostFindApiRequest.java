package com.example.jpaboard.post.controller.dto;

public class PostFindApiRequest {

    private String title;
    private String content;

    public PostFindApiRequest() {
        title = "";
        content = "";
    }

    public PostFindApiRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String title() {
        return title;
    }

    public String content() {
        return content;
    }

}
