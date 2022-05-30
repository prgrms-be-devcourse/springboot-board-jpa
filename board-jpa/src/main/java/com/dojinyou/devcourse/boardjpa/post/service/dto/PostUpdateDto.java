package com.dojinyou.devcourse.boardjpa.post.service.dto;

import com.dojinyou.devcourse.boardjpa.post.controller.dto.PostUpdateRequest;

public class PostUpdateDto {

    long userId;
    String title;
    String content;

    public long getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public static PostUpdateDto from(PostUpdateRequest postUpdateRequest) {
        return new Builder().userId(postUpdateRequest.getUserId())
                            .title(postUpdateRequest.getTitle())
                            .content(postUpdateRequest.getContent())
                            .build();
    }

    private PostUpdateDto(Builder builder) {
        this.userId = builder.userId;
        this.title = builder.title;
        this.content = builder.content;
    }

    public static class Builder {
        long userId;
        String title;
        String content;

        public Builder userId(long userId) {
            this.userId = userId;
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

        public PostUpdateDto build() {
            return new PostUpdateDto(this);
        }
    }
}
