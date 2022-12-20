package com.prgrms.java.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.prgrms.java.domain.Post;
import org.springframework.util.Assert;

public class CreatePostRequest {

    private final String title;
    private final String content;
    private final long userId;

    @JsonCreator
    public CreatePostRequest(@JsonProperty("title") String title, @JsonProperty("content") String content, @JsonProperty("userId") long userId) {
        Assert.notNull(title, "title should not null");
        Assert.notNull(content, "content should not null");
        this.title = title;
        this.content = content;
        this.userId = userId;
    }

    public Post toEntity() {
        return new Post(this.title, this.content);
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public long getUserId() {
        return userId;
    }
}
