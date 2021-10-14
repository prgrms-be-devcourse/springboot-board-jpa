package com.kdt.programmers.forum.transfer;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostDto {

    private final Long id;
    private final String title;
    private final String content;
    private LocalDateTime createdAt;
    private String createdBy;

    public PostDto(String title, String content) {
        this.id = null;
        this.title = title;
        this.content = content;
    }

    public PostDto(Long id, String title, String content, LocalDateTime createdAt, String createdBy) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
    }
}
