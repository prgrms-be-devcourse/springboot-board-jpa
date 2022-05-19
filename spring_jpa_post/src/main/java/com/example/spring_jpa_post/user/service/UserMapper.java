package com.example.spring_jpa_post.user.service;

import com.example.spring_jpa_post.user.entity.User;
import com.example.spring_jpa_post.user.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User convertUserFromUserDto(UserDto userDto) {
        return User.builder()
                .name(userDto.getName())
                .age(userDto.getAge())
                .hobby(userDto.getHobby())
                .build();
    }

    public UserDto convertUserDtoFromEntity(User user) {
        return UserDto.builder()
                .id(user.getId())
                .age(user.getAge())
                .name(user.getName())
                .hobby(user.getHobby())
                .build();
    }
}
