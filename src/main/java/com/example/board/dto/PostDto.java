package com.example.board.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class PostDto {
    private int id;
    private String title;
    private String content;
    private UserDto userDto;
    private LocalDateTime createdAt;
    private String createdBy;
}
