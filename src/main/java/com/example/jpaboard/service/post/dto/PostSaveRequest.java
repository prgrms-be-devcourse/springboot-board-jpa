package com.example.jpaboard.service.post.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class PostSaveRequest {
    @NotBlank(message = "제목은 필수 값입니다")
    private final String title;

    @NotBlank(message = "내용은 필수 값입니다")
    private final String content;

    @NotNull(message = "유저 아이디는 필수 값입니다")
    private final Long userId;

    public PostSaveRequest(Long userId, String title, String content) {
        this.userId = userId;
        this.title = title;
        this.content = content;
    }

    public Long getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
