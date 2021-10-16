package com.jpa.board.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class PostUpdateDto {
    private String title;
    private String content;
}
