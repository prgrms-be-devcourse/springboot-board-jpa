package com.example.board.converter;

import com.example.board.domain.Post;
import com.example.board.dto.PostDto;

import java.time.LocalDateTime;

public class PostConverter {
    public static Post convertFromDtoToPost(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setCreatedAt(LocalDateTime.now());
        post.setCreatedBy(postDto.getCreatedBy());

        return post;
    }

    public static PostDto convertFromPostToDto(Post post) {
        return PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .createdBy(post.getCreatedBy())
                .build();
    }
}
