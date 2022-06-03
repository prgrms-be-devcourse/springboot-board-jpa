package com.programmers.springbootboardjpa.dto.post.request;

import java.util.Optional;

public class PostUpdateRequest {

    private Optional<String> title;

    private Optional<String> content;

    public PostUpdateRequest(String title, String content) {
        if (title.isBlank()) {
            this.title = Optional.empty();
        } else {
            this.title = Optional.ofNullable(title);
        }

        if (content.isBlank()) {
            this.content = Optional.empty();
        } else {
            this.content = Optional.ofNullable(content);
        }
    }

    public Optional<String> getTitle() {
        return title;
    }

    public Optional<String> getContent() {
        return content;
    }
}
