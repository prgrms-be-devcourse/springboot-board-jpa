package com.prgrms.board.domain.post.dto.response;

import com.prgrms.board.domain.post.entity.Post;

import lombok.Builder;

@Builder
public record PostDetailResponse(
    Long postId,
    Long userId,
    String username,
    String title,
    String content
) {
    public static PostDetailResponse from(Post post) {
        return PostDetailResponse.builder()
            .postId(post.getId())
            .userId(post.getUser().getId())
            .username(post.getUser().getName())
            .title(post.getTitle())
            .content(post.getContent())
            .build();
    }
}
