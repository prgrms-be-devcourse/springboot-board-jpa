package com.example.boardjpa.dto;

public class UpdatePostRequestDto {
    private String content;

    public UpdatePostRequestDto(String content) {
        this.content = content;
    }

    public UpdatePostRequestDto() {
    }

    public String getContent() {
        return content;
    }
}
