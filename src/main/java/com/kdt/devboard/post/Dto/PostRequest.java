package com.kdt.devboard.post.Dto;

import com.kdt.devboard.post.domain.Post;
import com.kdt.devboard.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Builder
public class PostRequest {

    @NotBlank
    private final String title;

    @NotBlank
    private final String content;

    @NotNull
    private final Long userId;

    public Post toEntity(User user) {
        return Post.builder()
                .title(this.title)
                .content(this.content)
                .user(user)
                .build();
    }

}
