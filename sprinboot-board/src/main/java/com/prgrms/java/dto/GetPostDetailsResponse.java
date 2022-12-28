package com.prgrms.java.dto;

import com.prgrms.java.domain.Post;

public record GetPostDetailsResponse(long id, String title, String content) {

    public static GetPostDetailsResponse from(Post post) {
        return new GetPostDetailsResponse(post.getId(), post.getTitle(), post.getContent());
    }
}
