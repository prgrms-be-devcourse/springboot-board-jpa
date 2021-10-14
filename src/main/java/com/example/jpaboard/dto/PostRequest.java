package com.example.jpaboard.dto;

import lombok.Getter;

@Getter
public class PostRequest {

    private String title;
    private String content;
    private Long authorId;

    public PostRequest(String title, String content, Long authorId) {
        this.title = title;
        this.content = content;
        this.authorId = authorId;
    }
}
