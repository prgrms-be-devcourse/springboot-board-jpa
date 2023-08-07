package com.programmers.jpa_board.post.util;

import com.programmers.jpa_board.post.domain.Post;
import com.programmers.jpa_board.post.domain.dto.request.CreatePostRequest;
import com.programmers.jpa_board.post.domain.dto.response.PostResponse;


public class PostConverter {
    public static Post toEntity(CreatePostRequest dto) {
        return new Post(dto.title(), dto.content());
    }

    public static PostResponse toDto(Post post) {
        return new PostResponse(post.getId(), post.getTitle(), post.getContent(), post.getUser().getId(), post.getUser().getName(), post.getCreatedAt());
    }
}
