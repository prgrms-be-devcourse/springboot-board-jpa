package com.misson.jpa_board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class PostCreateRequest {
    private String title;
    private String content;
    private Long userId;
}
