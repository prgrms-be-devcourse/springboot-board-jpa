package com.spring.board.springboard.post.domain.dto;

import com.spring.board.springboard.post.domain.Post;
import com.spring.board.springboard.user.domain.Member;

import java.time.LocalDateTime;

public record RequestPostDto(String title, String content, Integer memberId){

    public RequestPostDto(Post post) {
        this(
                post.getTitle(),
                post.getContent(),
                post.getMemberId()
        );
    }

    public Post toEntity(RequestPostDto postDto, Member member) {
        return new Post(
                postDto.title,
                postDto.content,
                LocalDateTime.now(),
                member
        );
    }
}
