package com.example.boardjpa.dto;

public class CreatePostRequestDto {
    private final String title;
    private final String content;
    private final Long userId;

    public CreatePostRequestDto(String title, String content, Long userId) {
        this.title = title;
        this.content = content;
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Long getUserId() {
        return userId;
    }
}
