package com.board.project.dto;

import com.board.project.domain.Post;
import com.board.project.domain.User;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostRequest {

    @NotBlank
    private final String title;

    @NotBlank
    private final String content;

    @NotNull
    private final Long userId;

    public PostRequest(String title, String content, Long userId) {
        this.title = title;
        this.content = content;
        this.userId = userId;
    }

    public Post toEntity(User user) {
        return Post.builder()
            .title(this.title)
            .content(this.content)
            .user(user)
            .build();
    }
}
