package com.programmers.jpaboard.board.controller.dto;

import lombok.Builder;

@Builder
public class BoardResponseDto {
    private final String title;
    private final String content;
}
