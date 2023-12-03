package com.programmers.springbootboardjpa.dto;


import lombok.*;

@Getter
public class PostServiceRequestDto {
    private final String title;
    private final String content;
    private final Long userId;

    @Builder
    protected PostServiceRequestDto(String title, String content, Long userId) {
        this.title = title;
        this.content = content;
        this.userId = userId;
    }
}
