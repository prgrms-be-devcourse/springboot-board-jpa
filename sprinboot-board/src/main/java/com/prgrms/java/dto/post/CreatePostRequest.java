package com.prgrms.java.dto.post;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.prgrms.java.domain.Post;
import com.prgrms.java.domain.User;
import jakarta.validation.constraints.NotBlank;

public record CreatePostRequest(@NotBlank String title, @NotBlank String content, long userId) {

    public CreatePostRequest(@JsonProperty("title") String title, @JsonProperty("content") String content, @JsonProperty("userId") long userId) {
        this.title = title;
        this.content = content;
        this.userId = userId;
    }

    public Post toEntity(User user) {
        return new Post(this.title, this.content, user);
    }
}
