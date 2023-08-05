package com.example.jpaboard.post.controller.dto;

public class FindAllApiRequest {

    private String title;
    private String content;

    public FindAllApiRequest() {
        title = "";
        content = "";
    }

    public FindAllApiRequest(String title, String content) {
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
