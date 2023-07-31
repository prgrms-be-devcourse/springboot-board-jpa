package com.jpaboard.domain.post.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PostResponse(
        Long id,
        String title,
        String content,
        LocalDateTime create_at,
        LocalDateTime update_at
) {
}
