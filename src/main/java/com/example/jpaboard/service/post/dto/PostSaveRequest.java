package com.example.jpaboard.service.post.dto;

import javax.validation.constraints.NotBlank;

public class PostSaveRequest {
    @NotBlank(message = "제목은 필수 값입니다")
    private final String title;

    @NotBlank(message = "내용은 필수 값입니다")
    private final String content;

    public PostSaveRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
