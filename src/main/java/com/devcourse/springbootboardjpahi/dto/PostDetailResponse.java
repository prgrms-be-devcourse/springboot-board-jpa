package com.devcourse.springbootboardjpahi.dto;

import com.devcourse.springbootboardjpahi.domain.Post;
import com.devcourse.springbootboardjpahi.domain.User;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record PostDetailResponse(
        Long id,
        String title,
        String content,
        String authorName,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public static PostDetailResponse from(Post post) {
        User author = post.getUser();

        return PostDetailResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .authorName(author.getName())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }
}
