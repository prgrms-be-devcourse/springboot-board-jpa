package com.programmers.jpa_board.user.util;

import com.programmers.jpa_board.user.domain.User;
import com.programmers.jpa_board.user.domain.dto.UserDto;

public class UserConverter {
    public static User toEntity(UserDto.CreateUserRequest dto) {
        return new User(dto.name(), dto.age(), dto.hobby());
    }

    public static UserDto.UserResponse toDto(User user) {
        return new UserDto.UserResponse(user.getId(), user.getName(), user.getAge(), user.getHobby(), user.getPosts(), user.getCreatedAt());
    }
}
