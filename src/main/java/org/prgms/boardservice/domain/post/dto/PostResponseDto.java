package org.prgms.boardservice.domain.post.dto;

import org.prgms.boardservice.domain.post.Post;

public record PostResponseDto(Long id, String title, String content, Long userId) {

    public PostResponseDto(Post post) {
        this(post.getId(), post.getTitle().getValue(), post.getContent().getValue(), post.getUserId());
    }
}
