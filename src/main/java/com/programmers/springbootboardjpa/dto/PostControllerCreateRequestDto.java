package com.programmers.springbootboardjpa.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PostControllerCreateRequestDto {
    private final String title;
    private final String content;
    private final Long userId;
}
