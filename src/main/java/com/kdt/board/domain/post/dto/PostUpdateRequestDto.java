package com.kdt.board.domain.post.dto;

import com.kdt.board.domain.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import static lombok.AccessLevel.PROTECTED;

@Getter
@AllArgsConstructor(access = PROTECTED)
@Builder
public class PostUpdateRequestDto {

    private final String title;
    private final String content;

    public static Post from(PostUpdateRequestDto postRequestDto) {
        return Post.builder()
                .title(postRequestDto.getTitle())
                .content(postRequestDto.getContent())
                .build();
    }
}
