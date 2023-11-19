package com.example.board.converter;

import com.example.board.domain.User;
import com.example.board.dto.request.CreateUserRequest;
import com.example.board.dto.response.UserResponse;

public class UserConverter {

    public static User toUser(CreateUserRequest requestDto) {
        return User.builder()
                .name(requestDto.name())
                .age(requestDto.age())
                .hobby(requestDto.hobby())
                .build();
    }

    public static UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .age(user.getAge())
                .hobby(user.getHobby())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
