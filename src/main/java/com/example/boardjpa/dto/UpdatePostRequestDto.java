package com.example.boardjpa.dto;

public class UpdatePostRequestDto {
    private final String content;

    public UpdatePostRequestDto(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
