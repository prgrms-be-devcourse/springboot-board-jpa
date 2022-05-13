package com.example.boardjpa.dto;

import com.example.boardjpa.domain.Post;

import java.time.LocalDateTime;

public class PostResponseDto {
    private final Long id;
    private final String title;
    private final String content;
    private final UserResponseDto user;
    private final LocalDateTime createdAt;
    private final String createdBy;

    public PostResponseDto(Post entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.user = new UserResponseDto(entity.getUser());
        this.createdAt = entity.getCreatedAt();
        this.createdBy = entity.getCreatedBy();
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

    public UserResponseDto getUser() {
        return user;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }
}
