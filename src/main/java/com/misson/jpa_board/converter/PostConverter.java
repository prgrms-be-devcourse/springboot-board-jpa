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

    // dto -> entity
    public Post convertToPostCreateRequest(PostCreateRequest postDto, User user) {
        Post post = Post.builder()
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .build();
        post.setUser(user);
        post.setInfo(user.getId());
        return post;
    }

    public PostDto convertPostDtoFromPostCreateRequest(PostCreateRequest postCreateRequest) {
        return PostDto.builder()
                .content(postCreateRequest.getContent())
                .title(postCreateRequest.getTitle())
                .userId(postCreateRequest.getUserId())
                .build();
    }

    // entity -> dto
    public PostDto convertPostDto(Post post) {
        return PostDto.builder()
                .id(post.getId())
                .content(post.getContent())
                .title(post.getTitle())
                .userId(userConverter.convertUserDto(post.getUser()).getId())
                .build();
    }
}
