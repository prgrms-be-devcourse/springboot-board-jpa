package org.prgms.boardservice.domain.post.dto;

import org.prgms.boardservice.domain.post.Post;

public record PostResponse(Long id, String title, String content, Long userId) {

    public PostResponse(Post post) {
        this(post.getId(), post.getTitle().getTitle(), post.getContent().getContent(), post.getUserId());
    }
}
