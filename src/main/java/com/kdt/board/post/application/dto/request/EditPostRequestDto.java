package com.kdt.board.post.application.dto.request;

public class EditPostRequestDto {

    private final Long userId;
    private final Long postId;
    private final String title;
    private final String content;

    private EditPostRequestDto(Builder builder, Long postId) {
        this.userId = builder.userId;
        this.postId = postId;
        this.title = builder.title;
        this.content = builder.content;
    }

    public Long getUserId() {
        return userId;
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

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long userId;
        private Long postId;
        private String title;
        private String content;

        public Builder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public Builder postId(Long postId) {
            this.postId = postId;
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

        public EditPostRequestDto build() {
            return new EditPostRequestDto(this, postId);
        }
    }
}
