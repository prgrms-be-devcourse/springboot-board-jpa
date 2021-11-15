package com.maenguin.kdtbbs.dto;

import lombok.Getter;

@Getter
public class PostDto {

    private Long id;

    private String title;

    private String content;

    public PostDto(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }
}
