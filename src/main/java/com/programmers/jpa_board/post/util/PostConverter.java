package com.programmers.jpa_board.post.util;

import com.programmers.jpa_board.post.domain.Post;
import com.programmers.jpa_board.post.domain.dto.PostDto;


public class PostConverter {
    public static Post toEntity(PostDto.CreatePostRequest dto) {
        return new Post(dto.title(), dto.content());
    }

    public static PostDto.CommonResponse toDto(Post post) {
        return new PostDto.CommonResponse(post.getId(), post.getTitle(), post.getContent(), post.getUser().getId(), post.getUser().getName(), post.getCreatedAt());
    }
}
