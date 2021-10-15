package com.eunu.springbootboard.dao.user;

import com.eunu.springbootboard.dao.post.PostConverter;
import com.eunu.springbootboard.entity.User;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    PostConverter postConverter = new PostConverter();

    public User convertUser(UserDto userDto) {
        User user = new User(
            userDto.getId(),
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
            .id(user.getId())
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
