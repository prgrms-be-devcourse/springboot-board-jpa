package com.kdt.bulletinboard.converter;

import com.kdt.bulletinboard.dto.PostDto;
import com.kdt.bulletinboard.entity.Post;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PostConverter {

    private final UserConverter userConverter;

    public PostConverter(UserConverter userConverter) {
        this.userConverter = userConverter;
    }

    public Post convertToPost(PostDto postDto) {
        Post post = new Post(postDto.getTitle(), postDto.getContent(), LocalDateTime.now().toString());
        post.setUser(userConverter.convertToUser(postDto.getUserDto()));
        return post;
    }

    public PostDto convertToPostDto(Post post) {
        return PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .userDto(this.userConverter.convertToUserDto(post.getUser()))
                .build();
    }

}
