package com.programmers.springbootboardjpa.dto.post.request;

import com.programmers.springbootboardjpa.domain.post.Post;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class PostCreationRequest {

    @NotNull
    private Long userId;

    @NotBlank(message = "제목은 필수값입니다.")
    private String title;

    @NotBlank(message = "본문 내용은 필수값입니다.")
    private String content;

    public PostCreationRequest(Long userId, String title, String content) {
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

    public Post toEntity() {
        return new Post(userId.toString(),
                LocalDateTime.now(),
                title,
                content);
    }

}
