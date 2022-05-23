package com.dojinyou.devcourse.boardjpa.post.controller.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class PostCreateRequest {
    @NotNull
    @Positive
    private long userId;

    @NotBlank
    @Length(max = 50)
    private String title;

    @NotBlank
    private String content;

    protected PostCreateRequest() {
    }

    public PostCreateRequest(long userId, String title, String content) {
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
