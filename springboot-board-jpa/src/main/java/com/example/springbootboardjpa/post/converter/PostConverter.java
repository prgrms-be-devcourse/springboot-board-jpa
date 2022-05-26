package com.example.springbootboardjpa.post.converter;

import com.example.springbootboardjpa.domain.Post;
import com.example.springbootboardjpa.domain.User;
import com.example.springbootboardjpa.post.dto.PostDto;
import com.example.springbootboardjpa.post.dto.PostUserDto;
import org.springframework.stereotype.Component;

@Component
public class PostConverter {
    public Post convertPost(PostDto postDto) {
        Post post = Post.builder()
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .build();
        post.setUser(convertUser(postDto.getPostUserDto()));
        return post;
    }

    private User convertUser(PostUserDto postUserDto) {
        return User.builder()
                .id(postUserDto.getId())
                .name(postUserDto.getName())
                .build();
    }

    public PostDto convertPostDto(Post post) {
        return PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .postUserDto(PostUserDto.builder()
                        .id(post.getUser().getId())
                        .name(post.getUser().getName())
                        .build())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }
}
