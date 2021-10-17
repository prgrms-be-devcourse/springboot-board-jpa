package com.maenguin.kdtbbs.dto;

import lombok.Getter;

@Getter
public class PostAddDto {

    private Long userId;
    private String title;
    private String content;

    public PostAddDto(Long userId, String title, String content) {
        this.userId = userId;
        this.title = title;
        this.content = content;
    }

}
