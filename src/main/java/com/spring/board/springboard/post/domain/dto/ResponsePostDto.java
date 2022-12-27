package com.spring.board.springboard.post.domain.dto;

import com.spring.board.springboard.post.domain.Post;
import com.spring.board.springboard.user.domain.MemberResponseDto;

import java.time.LocalDateTime;
public record ResponsePostDto(Integer postId, String title, String content, LocalDateTime createdAt, MemberResponseDto member){
    public ResponsePostDto(Post post, MemberResponseDto member){
        this(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getCreatedAt(),
                member
        );
    }

}
