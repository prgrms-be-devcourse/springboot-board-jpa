package org.prgms.springbootboardjpayu.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

public record PostResponse(Long id, String title, String content, LocalDateTime createdAt, UserProfile user
) {
    @Builder
    public PostResponse(Long id, String title, String content, LocalDateTime createdAt, UserProfile user) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.user = user;
    }
}
