package com.kdt.board.domain.post.dto;

import com.kdt.board.domain.post.entity.Post;
import com.kdt.board.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import static lombok.AccessLevel.PROTECTED;

@Getter
@AllArgsConstructor(access = PROTECTED)
@Builder
public class PostCreateRequestDto {

    private final String title;
    private final String content;
    private final Long userId;

    public static Post from(PostCreateRequestDto postRequestDto, User user) {
        return Post.builder()
                .title(postRequestDto.getTitle())
                .content(postRequestDto.getContent())
                .user(user)
                .build();
    }
}
