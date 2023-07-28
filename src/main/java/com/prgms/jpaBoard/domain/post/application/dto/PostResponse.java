package com.prgms.jpaBoard.domain.post.application.dto;

import com.prgms.jpaBoard.domain.post.Post;

import java.time.ZonedDateTime;

public record PostResponse(
        Long id,
        String title,
        String content,
        ZonedDateTime createdAt,
        String createdBy)
{
    public PostResponse(Post post){
        this(post.getId(), post.getTitle(), post.getContent(), post.getCreatedAt(), post.getCreatedBy());
    }
}
