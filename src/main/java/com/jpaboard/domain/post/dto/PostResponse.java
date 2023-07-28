package com.jpaboard.domain.post.dto;

import lombok.Builder;

import java.time.LocalDateTime;

public record PostResponse(
        Long id,
        String title,
        String content,
        LocalDateTime create_at,
        LocalDateTime update_at
) {

    @Builder
    public PostResponse(Long id, String title, String content, LocalDateTime create_at, LocalDateTime update_at) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.create_at = create_at;
        this.update_at = update_at;
    }
}
