package org.programmers.dev.domain.post.controller.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;
import org.programmers.dev.domain.post.domain.entity.Post;
import org.programmers.dev.domain.user.UserResponse;

@Data
public class PostResponse {

    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private String createdBy;
    private UserResponse user;

    @Builder
    private PostResponse(
        Long id, String title, String content, UserResponse user, LocalDateTime createdAt,
        String createdBy
    ) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.user = user;
    }

    public static PostResponse from(Post post) {
        return PostResponse.builder()
            .id(post.getId())
            .title(post.getTitle())
            .content(post.getContent())
            .createdAt(post.getCreatedAt())
            .createdBy(post.getCreatedBy())
            .user(UserResponse.from(post.getUser()))
            .build();
    }
}
