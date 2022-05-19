package com.example.board.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostDto {
    private Long id;
    private String title;
    private String content;
    private UserDto author;
}

