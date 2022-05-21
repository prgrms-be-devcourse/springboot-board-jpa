package com.example.jpaboard.service.dto.post;

import org.hibernate.validator.constraints.Length;
import org.springframework.lang.Nullable;
import javax.validation.constraints.NotNull;

public class PostUpdateRequest {

    @NotNull(message = "유저 아이디는 필수 값입니다")
    private final Long userId;

    @Nullable
    @Length(min = 1, message = "제목은 한 글자 이상입니다")
    private final String title;

    @Nullable
    @Length(min = 1, message = "내용은 한 글자 이상입니다")
    private final String content;

    public PostUpdateRequest(Long userId, String title, String content) {
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
