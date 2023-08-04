package org.prgms.boardservice.domain.post.dto;

import org.prgms.boardservice.domain.post.Post;

public record PostCreateRequestDto(String title, String content, Long userId) {

    public Post toEntity() {
        return Post.builder()
                .title(title)
                .content(content)
                .userId(userId)
                .build();
    }
}
