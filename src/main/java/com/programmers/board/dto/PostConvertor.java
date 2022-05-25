package com.programmers.board.dto;

import com.programmers.board.entity.Post;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PostConvertor {
    public static Post postRequestConvertor(PostRequestDto postRequestDto) {
        if (postRequestDto.getTitle() == null) {
            throw new IllegalArgumentException("제목이 없습니다.");
        }
        if (postRequestDto.getContent() == null) {
            throw new IllegalArgumentException("내용이 없습니다.");
        }
        if (postRequestDto.getUserId() == null) {
            throw new IllegalArgumentException("유저 정보가 없습니다.");
        }
        return new Post(postRequestDto.getTitle(), postRequestDto.getContent());
    }

    public static PostResponseDto postResponseConvertor(Post post) {
        return new PostResponseDto(post.getTitle(), post.getContent(), post.getCreatedAt().toString(), post.getCreatedBy());
    }
}
