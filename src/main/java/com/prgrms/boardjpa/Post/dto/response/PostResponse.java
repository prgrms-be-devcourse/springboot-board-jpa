package com.prgrms.boardjpa.Post.dto.response;

import com.prgrms.boardjpa.Post.domain.Post;

public record PostResponse(Long id, String title, String content, Long authorId) {

    public static PostResponse create(Post post) {
        return new PostResponse(post.getId(), post.getTitle(), post.getContent(), post.getUser().getId());
    }

}