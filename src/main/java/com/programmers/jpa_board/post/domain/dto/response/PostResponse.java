package com.programmers.jpa_board.post.domain.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

public class PostResponse {
    private Long id;
    private String title;
    private String content;
    private Long userId;
    private String createdBy;
    private LocalDateTime createAt;

    @Builder
    public PostResponse(Long id, String title, String content, Long userId, String createdBy, LocalDateTime createAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.userId = userId;
        this.createdBy = createdBy;
        this.createAt = createAt;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Long getUserId() {
        return userId;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }
}
