package com.dojinyou.devcourse.boardjpa.post.controller.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class PostUpdateRequest {

    @NotNull
    @Positive
    private final long userId;

    @Length(max = 50)
    private final String title;

    private final String content;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public PostUpdateRequest(
            @JsonProperty(value = "userId") long userId,
            @JsonProperty(value = "title") String title,
            @JsonProperty(value = "content") String content) {
        this.userId = userId;
        this.title = title;
        this.content = content;
    }

    public long getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
