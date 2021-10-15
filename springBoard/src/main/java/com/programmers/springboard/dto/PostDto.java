package com.programmers.springboard.dto;

import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class PostDto {
    private String title;
    private String content;
    private UserDto userDto;

    public PostDto update(String title, String content) {
        this.title = title;
        this.content = content;
        return this;
    }
}
