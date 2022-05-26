package com.example.springbootboardjpa.user.converter;

import com.example.springbootboardjpa.domain.User;
import com.example.springbootboardjpa.user.dto.UserDto;
import com.example.springbootboardjpa.user.dto.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {
    public User convertUser(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .age(userDto.getAge())
                .name(userDto.getName())
                .hobby(userDto.getHobby())
                .build();
    }

    public UserResponse convertUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .age(user.getAge())
                .hobby(user.getHobby())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
