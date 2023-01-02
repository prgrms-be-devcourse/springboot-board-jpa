package com.spring.board.springboard.post.domain.dto;

import com.spring.board.springboard.post.domain.Post;
import com.spring.board.springboard.user.domain.dto.MemberSummaryResponseDto;

import java.time.LocalDateTime;

public record PostDetailResponseDto(Integer postId, String title, String content, LocalDateTime createdAt,
                                    MemberSummaryResponseDto member) {

    public PostDetailResponseDto(Post post, MemberSummaryResponseDto member) {
        this(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getCreatedAt(),
                member
        );
    }

}
