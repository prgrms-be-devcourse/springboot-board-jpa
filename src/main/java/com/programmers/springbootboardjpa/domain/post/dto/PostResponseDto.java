package com.programmers.springbootboardjpa.domain.post.dto;

import com.programmers.springbootboardjpa.domain.post.domain.Post;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PostResponseDto(Long id, String title, String content, Long userId, String createdBy, LocalDateTime createdAt) {

    public static PostResponseDto convertPostResponseDto(Post post) {
        return PostResponseDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .userId(post.getUser().getId())
                .createdBy(post.getCreatedBy())
                .createdAt(post.getCreatedAt())
                .build();
    }
}
