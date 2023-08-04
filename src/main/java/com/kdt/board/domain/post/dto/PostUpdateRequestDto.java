package com.kdt.board.domain.post.dto;

import com.kdt.board.domain.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostUpdateRequestDto {

    private String title;
    private String content;

    public static Post from(PostUpdateRequestDto postRequestDto) {
        return Post.builder()
                .title(postRequestDto.getTitle())
                .content(postRequestDto.getContent())
                .build();
    }
}
