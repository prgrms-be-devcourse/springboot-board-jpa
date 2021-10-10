package com.homework.springbootboard.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostDto {
    private Long id;
    private String title;
    private String content;
    private UserDto userDto;

    @Builder
    public PostDto(Long id, String title, String content, UserDto userDto) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.userDto = userDto;
    }
}
