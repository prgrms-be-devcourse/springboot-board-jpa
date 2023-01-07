package com.example.bulletin_board_jpa.post.dto;

import lombok.Getter;

@Getter
public class PostResponseDto {
    private String title;
    private String content;

    public PostResponseDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
