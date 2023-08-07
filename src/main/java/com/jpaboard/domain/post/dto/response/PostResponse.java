package com.jpaboard.domain.post.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PostResponse(
        Long id,
        String name,
        String title,
        String content,
        LocalDateTime createAt,
        LocalDateTime updateAt
) {
}
