package com.kdt.user.converter;

import com.kdt.post.dto.PostDto;
import com.kdt.post.model.Post;
import com.kdt.user.dto.UserDto;
import com.kdt.user.model.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Component
public class UserConverter {
    public User convertUserDto(UserDto userDto) {
        LocalDateTime now = LocalDateTime.now();

        User user = User.builder()
                .name(userDto.getName())
                .age(userDto.getAge())
                .hobby(userDto.getHobby())
                .createdAt(now)
                .createdBy(userDto.getName())
                .lastUpdatedAt(now)
                .build();

        return user;
    }

    public UserDto convertUser(User user){
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .age(user.getAge())
                .hobby(user.getHobby())
                .createdBy(user.getCreatedBy())
                .createdAt(user.getCreatedAt())
                .lastUpdatedAt(user.getLastUpdatedAt())
                .postDtos(user.getPosts().stream()
                        .map(this::convertPostDto)
                        .collect(Collectors.toList()))
                .build();
    }

    private PostDto convertPostDto(Post post) {
        return PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .createdBy(post.getCreatedBy())
                .createdAt(post.getCreatedAt())
                .lastUpdatedAt(post.getLastUpdatedAt())
                .build();
    }
}
