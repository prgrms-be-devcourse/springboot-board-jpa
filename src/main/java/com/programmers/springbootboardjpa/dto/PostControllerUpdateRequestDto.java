package com.programmers.springbootboardjpa.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PostControllerUpdateRequestDto {
    private final String title;
    private final String content;
}
