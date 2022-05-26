package com.dojinyou.devcourse.boardjpa.post.service.dto;

import java.time.LocalDateTime;

public class PostResponseDto {
    long id;
    String title;
    String content;

    LocalDateTime createdAt;

    LocalDateTime updatedAt;

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    private PostResponseDto(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.content = builder.content;
        this.createdAt = builder.createdAt;
        this.updatedAt = builder.updatedAt;
    }

    public static class Builder {
        long id;
        String title;

        String content;

        LocalDateTime createdAt;

        LocalDateTime updatedAt;

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public PostResponseDto build() {
            return new PostResponseDto(this);
        }
    }
}
