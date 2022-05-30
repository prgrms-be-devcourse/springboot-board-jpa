package com.dojinyou.devcourse.boardjpa.post.controller.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class PostUpdateRequest {

    @NotNull
    @Positive
    private long userId;

    @Length(max = 50)
    private String title;

    private String content;

    protected PostUpdateRequest() {
    }

    public PostUpdateRequest(long userId, String title, String content) {
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
