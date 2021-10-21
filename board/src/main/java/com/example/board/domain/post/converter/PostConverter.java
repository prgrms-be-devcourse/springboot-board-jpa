package com.example.board.domain.post.converter;

import com.example.board.domain.post.domain.Post;
import com.example.board.domain.post.dto.PostDto;
import com.example.board.domain.user.converter.UserConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class PostConverter {

    private final UserConverter userConverter;

    // Dto -> Entity
    public Post convertPost(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setUser(userConverter.convertUser(postDto.getUserDto()));
        post.setCreatedAt(LocalDateTime.now());

        return post;
    }

    // Entity -> Dto
    public PostDto convertPostDto(Post post) {
        return PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .userDto(userConverter.convertUserDto(post.getUser()))
                .createdAt(post.getCreatedAt())
                .build();
    }
}
