package com.programmers.springbootboardjpa.dto.post.response;

import java.time.LocalDateTime;

public class PostResponse {

    private Long postId;

    private String title;

    private String content;

    private String createdBy;

    private LocalDateTime cratedAt;

    private UserResponse userResponse;

    public PostResponse(Long postId, String title, String content, String createdBy, LocalDateTime cratedAt, UserResponse userResponse) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.createdBy = createdBy;
        this.cratedAt = cratedAt;
        this.userResponse = userResponse;
    }

    public static class PostResponseBuilder {

        private Long postId;

        private String title;

        private String content;

        private String createdBy;

        private LocalDateTime cratedAt;

        private UserResponse userResponse;

        public PostResponseBuilder postId(Long postId) {
            this.postId = postId;
            return this;
        }

        public PostResponseBuilder title(String title) {
            this.title = title;
            return this;
        }

        public PostResponseBuilder content(String content) {
            this.content = content;
            return this;
        }

        public PostResponseBuilder createdBy(String createdBy) {
            this.createdBy = createdBy;
            return this;
        }

        public PostResponseBuilder cratedAt(LocalDateTime cratedAt) {
            this.cratedAt = cratedAt;
            return this;
        }

        public PostResponseBuilder userResponse(UserResponse userResponse) {
            this.userResponse = userResponse;
            return this;
        }

        public PostResponse build() {
            return new PostResponse(this.postId, this.title, this.content, this.createdBy, this.cratedAt, this.userResponse);
        }

    }

    public Long getPostId() {
        return postId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public LocalDateTime getCratedAt() {
        return cratedAt;
    }

    public UserResponse getUserResponse() {
        return userResponse;
    }
}
