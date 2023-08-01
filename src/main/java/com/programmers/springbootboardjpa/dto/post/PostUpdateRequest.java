package com.programmers.springbootboardjpa.dto.post;

import lombok.Getter;

@Getter
public class PostUpdateRequest {

    private String title;
    private String content;

    public PostUpdateRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }
    
}
