package com.example.jpaboard.service.post.dto;

import org.hibernate.validator.constraints.Length;
import org.springframework.lang.Nullable;

public class PostUpdateRequest {

    @Nullable
    @Length(min = 1, message = "제목은 한 글자 이상입니다")
    private final String title;

    @Nullable
    @Length(min = 1, message = "내용은 한 글자 이상입니다")
    private final String content;

    public PostUpdateRequest(String title, String content) {
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
