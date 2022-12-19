package com.prgrms.boardjpa.domain.post.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PostsResponseDto {
    private final List<PostResponseDto> posts;

    public static PostsResponseDto from(List<PostResponseDto> posts) {
        return new PostsResponseDto(posts);
    }
}
