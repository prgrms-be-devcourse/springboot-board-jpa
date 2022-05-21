package com.kdt.prgrms.board.dto;

import com.kdt.prgrms.board.entity.post.Post;

public class PostResponse {

    private final long userId;
    private final String userName;
    private final long postId;
    private final String title;
    private final String content;

    private PostResponse(PostResponseBuilder builder) {

        this.userId = builder.userId;
        this.userName = builder.userName;
        this.postId = builder.postId;
        this.title = builder.title;
        this.content = builder.content;
    }

    public static PostResponse from(Post post) {

        return PostResponse.builder()
                .userId(post.getUserId())
                .userName(post.getUserName())
                .postId(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }

    public static class PostResponseBuilder {

        private long userId;
        private String userName;
        private long postId;
        private String title;
        private String content;

        public PostResponseBuilder userId(long value) {

            this.userId = value;
            return this;
        }

        public PostResponseBuilder userName(String value) {

            this.userName = value;
            return this;
        }

        public PostResponseBuilder postId(long value) {

            this.postId = value;
            return this;
        }

        public PostResponseBuilder title(String value) {

            this.title = value;
            return this;
        }

        public PostResponseBuilder content(String value) {

            this.content = value;
            return this;
        }

        public PostResponse build() {

            return new PostResponse(this);
        }
    }

    public static PostResponseBuilder builder() {

        return new PostResponseBuilder();
    }

    public long getUserId() {

        return userId;
    }

    public String getUserName() {

        return userName;
    }

    public long getPostId() {

        return postId;
    }

    public String getTitle() {

        return title;
    }

    public String getContent() {

        return content;
    }
}
