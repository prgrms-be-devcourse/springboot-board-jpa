package com.programmers.board.core.post.application.dto;

public class PostUpdateRequest {

    private final String title;

    private final String content;

    public PostUpdateRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }

    //Getter
    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    //Builder
    public static PostUpdateRequestBuilder builder() {
        return new PostUpdateRequestBuilder();
    }

    public static class PostUpdateRequestBuilder {

        private String title;

        private String content;

        public PostUpdateRequestBuilder() {}

        public PostUpdateRequestBuilder title(String title) {
            this.title = title;
            return this;
        }

        public PostUpdateRequestBuilder content(String content) {
            this.content = content;
            return this;
        }

        public PostUpdateRequest build() {
            return new PostUpdateRequest(this.title, this.content);
        }
    }

}
