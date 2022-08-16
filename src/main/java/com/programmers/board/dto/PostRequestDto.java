package com.programmers.board.dto;

import com.programmers.board.entity.Post;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PostRequestDto {
    private final String title;
    private final String content;
    private final Long userId;
}
