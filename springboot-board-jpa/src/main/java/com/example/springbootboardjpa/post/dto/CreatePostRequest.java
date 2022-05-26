package com.example.springbootboardjpa.post.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreatePostRequest {
    private String title;
    private String content;
    private UUID userId;

    @Builder
    public CreatePostRequest(String title, String content, UUID userId) {
        this.title = title;
        this.content = content;
        this.userId = userId;
    }
}
