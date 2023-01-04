package com.spring.board.springboard.post.domain.dto;

import com.spring.board.springboard.post.domain.Post;
import com.spring.board.springboard.user.domain.Member;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record PostCreateRequestDto (
        @NotBlank String title,
        @NotBlank String content){

    public Post toEntity(Member member){
        return new Post(
                title,
                content,
                LocalDateTime.now(),
                member
        );
    }
}
