package com.kdt.board.post.application.dto.request;

import com.kdt.board.post.domain.Post;
import com.kdt.board.user.domain.User;

public class WritePostRequestDto {

    private final Long userId;
    private final String title;
    private final String content;

    private WritePostRequestDto(Builder builder) {
        this.userId = builder.userId;
        this.title = builder.title;
        this.content = builder.content;
    }

    public Long getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long userId;
        private String title;
        private String content;

        public Builder userId(Long userId) {
            this.userId = userId;
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

        public WritePostRequestDto build() {
            return new WritePostRequestDto(this);
        }
    }

    public Post of(User user) {
        return Post.builder()
                .title(getTitle())
                .content(getContent())
                .user(user)
                .build();
    }
}
