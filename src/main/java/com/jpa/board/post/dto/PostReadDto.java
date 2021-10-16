package com.jpa.board.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Builder
@Getter
public class PostReadDto {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private String createdBy;
}
