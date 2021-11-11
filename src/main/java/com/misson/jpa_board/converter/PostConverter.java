package com.misson.jpa_board.converter;

import com.misson.jpa_board.domain.Post;
import com.misson.jpa_board.domain.User;
import com.misson.jpa_board.dto.PostCreateRequest;
import com.misson.jpa_board.dto.PostDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PostConverter {

    private final UserConverter userConverter;

    public PostConverter(UserConverter userConverter) {
        this.userConverter = userConverter;
    }

    public Post convertToPostCreateRequest(PostCreateRequest postDto, User user) {
        Post post = Post.builder()
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .build();
        post.setUser(user);
        return post;
    }

    public PostDto convertPostDtoFromPostCreateRequest(PostCreateRequest postCreateRequest) {
        return new PostDto(postCreateRequest.getTitle(),
                postCreateRequest.getContent(),
                postCreateRequest.getUserId());
    }

    public PostDto convertPostDto(Post post) {
        return new PostDto(post.getId(),
                post.getTitle(),
                post.getContent(),
                userConverter.convertUserDto(post.getUser()).getId());
    }
}
