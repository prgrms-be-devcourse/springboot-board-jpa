package org.prgrms.myboard.dto;

import org.prgrms.myboard.domain.Post;

import java.time.LocalDateTime;

public record PostResponseDto(
    Long id,
    String title,
    String content,
    String createdBy,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
    public static PostResponseDto from(Post post) {
        return new PostResponseDto(post.getId(), post.getTitle(), post.getContent(), post.getCreatedBy(), post.getCreatedAt(), post.getUpdatedAt());
    }
}
