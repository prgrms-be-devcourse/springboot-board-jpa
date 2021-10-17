package com.maenguin.kdtbbs.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class PostListDto {

    List<PostDto> postDtoList;

    public PostListDto(List<PostDto> postDtoList) {
        this.postDtoList = postDtoList;
    }
}
