package com.kdt.devboard.post.Dto;

import com.kdt.devboard.post.domain.Post;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
public class PostResponse {

    private final Long postId;
    private final String title;
    private final String content;
    private final Long userId;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public PostResponse(Post entity) {
        this.postId = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.userId = entity.getUser().getId();
        this.createdAt = entity.getCreatedAt();
        this.updatedAt = entity.getUpdatedAt();
    }

}
