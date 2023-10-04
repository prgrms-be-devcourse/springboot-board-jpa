package com.programmers.springbootboardjpa.dto.post;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PostResponse {

    private final Long id;
    private final String title;
    private final String content;
    private final String createdBy;
    private final LocalDateTime createdAt;

    @Builder
    public PostResponse(Long id, String title, String content, String createdBy, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
    }

}
