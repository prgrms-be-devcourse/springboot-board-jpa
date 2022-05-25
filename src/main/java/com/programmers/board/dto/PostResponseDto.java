package com.programmers.board.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PostResponseDto {
    private final String title;
    private final String content;
    private final String createdAt;
    private final String createdBy;
}
