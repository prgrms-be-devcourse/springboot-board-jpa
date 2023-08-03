package com.prgrms.board.domain.post.dto.response;

import com.prgrms.board.domain.post.entity.Post;

import lombok.Builder;

@Builder
public record PostResponse(
    Long postId,
    String title,
    String content
) {
    public static PostResponse from(Post post) {
        return PostResponse.builder()
            .postId(post.getId())
            .title(post.getTitle())
            .content(post.getContent())
            .build();
    }
}
