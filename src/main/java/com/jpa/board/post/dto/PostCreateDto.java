package com.jpa.board.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class PostCreateDto {
    private String title;
    private String content;

    private Long userId;
}
