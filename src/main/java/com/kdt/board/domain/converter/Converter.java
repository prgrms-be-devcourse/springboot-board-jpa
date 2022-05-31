package com.kdt.board.domain.converter;

import com.kdt.board.domain.dto.PostDto;
import com.kdt.board.domain.dto.UserDto;
import com.kdt.board.domain.model.Post;
import com.kdt.board.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Converter {
    private final UserConvertService userConvertService;

    public Post convertPost(PostDto.SaveRequest postDto) {
        return Post.builder()
                .title(postDto.title())
                .content(postDto.content())
                .user(userConvertService.entityFindById(postDto.userId()))
                .build();
    }

    public User convertUser(UserDto.SaveRequest userDto) {
        return User.builder()
                .name(userDto.name())
                .age(userDto.age())
                .hobby(userDto.hobby())
                .build();
    }

    public PostDto.Response convertPostDto(Post post) {
        return new PostDto.Response(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                convertUserDto(post.getUser()),
                post.getCreatedAt(),
                post.getUpdatedAt(),
                post.getCreatedBy());
    }

    public UserDto.Response convertUserDto(User user) {
        return new UserDto.Response(
                user.getId(),
                user.getName(),
                user.getAge(),
                user.getHobby(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.getCreatedBy());
    }
}
