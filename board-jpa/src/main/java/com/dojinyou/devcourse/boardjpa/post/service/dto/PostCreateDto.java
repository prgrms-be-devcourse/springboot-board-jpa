package com.dojinyou.devcourse.boardjpa.post.service.dto;

public class PostCreateDto {
    private final String title;
    private final String content;

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    private PostCreateDto(Builder builder) {
        this.title = builder.title;
        this.content = builder.content;
    }

    public static class Builder {
        String title;
        String content;

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public PostCreateDto build() {
            return new PostCreateDto(this);
        }
    }
}
