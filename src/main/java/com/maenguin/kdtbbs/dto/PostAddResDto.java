package com.maenguin.kdtbbs.dto;

import lombok.Getter;

@Getter
public class PostAddResDto {

    private final Long postId;

    public PostAddResDto(Long postId) {
        this.postId = postId;
    }
}
