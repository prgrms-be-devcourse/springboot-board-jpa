package com.example.boardbackend.dto.converter;

import com.example.boardbackend.domain.Post;
import com.example.boardbackend.dto.PostDto;
import org.springframework.stereotype.Component;

@Component
public class PostConverter {
    // Entity -> DTO
    public PostDto convertToDto(Post post){
        return PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .user(post.getUser())
                .createAt(post.getCreatedAt())
                .build();
    }

    // DTO -> Entity
    public Post convetToEntity(PostDto postDto){
        Post post = new Post();
        post.setId(postDto.getId());
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setUser(postDto.getUser());
        post.setCreatedAt(postDto.getCreateAt());
        return post;
    }
}
