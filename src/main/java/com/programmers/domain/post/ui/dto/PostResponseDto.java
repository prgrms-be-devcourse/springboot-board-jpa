package com.programmers.domain.post.ui.dto;

public record PostResponseDto (
        Long postId,
        String title,
        String content,
        Long userId
){
}
