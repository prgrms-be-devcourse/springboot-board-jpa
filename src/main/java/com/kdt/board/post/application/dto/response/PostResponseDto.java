package com.kdt.board.post.application.dto.response;

import java.time.LocalDateTime;

public class PostResponseDto {

    private final long id;
    private final String title;
    private final String content;
    private final String author;
    private final LocalDateTime createdAt;

    private PostResponseDto(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.content = builder.content;
        this.author = builder.author;
        this.createdAt = builder.createdAt;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private  long id;
        private  String title;
        private  String content;
        private  String author;
        private  LocalDateTime createdAt;

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder author(String author) {
            this.author = author;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public PostResponseDto build() {
            return new PostResponseDto(this);
        }
    }
}
