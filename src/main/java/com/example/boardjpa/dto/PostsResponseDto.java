package com.example.boardjpa.dto;

import java.util.List;

public class PostsResponseDto {
    private final Integer page;
    private final Integer size;
    private final List<PostResponseDto> posts;

    public PostsResponseDto(Integer page, Integer size, List<PostResponseDto> posts) {
        this.page = page;
        this.size = size;
        this.posts = posts;
    }

    public Integer getPage() {
        return page;
    }

    public Integer getSize() {
        return size;
    }

    public List<PostResponseDto> getPosts() {
        return posts;
    }
}
