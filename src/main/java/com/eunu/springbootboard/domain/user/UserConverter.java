package com.eunu.springbootboard.domain.user;

import com.eunu.springbootboard.domain.post.PostConverter;
import com.eunu.springbootboard.entity.User;
import java.util.stream.Collectors;

public class UserConverter {

    PostConverter postConverter = new PostConverter();

    public User convertUser(UserDto userDto) {
        User user = new User(
            userDto.getUserId(),
            userDto.getName(),
            userDto.getAge(),
            userDto.getHobby(),
            userDto.getCreatedAt(),
            userDto.getCreatedBy()
        );

        userDto.getPostDtos().stream()
            .map(postDto -> {
                return postConverter.convertPost(postDto);
            })
            .collect(Collectors.toList())
            .forEach(user::addPost);

        return user;
    }

    public UserDto convertUserDto(User user) {
        return UserDto.builder()
            .userId(user.getUserId())
            .name(user.getName())
            .age(user.getAge())
            .hobby(user.getHobby())
            .postDtos(user.getPosts().stream()
                .map(postConverter::convertPostDto)
                .collect(Collectors.toList())
            )
            .build();
    }

}
