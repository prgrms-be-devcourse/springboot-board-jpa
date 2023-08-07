package com.programmers.jpa_board.user.util;

import com.programmers.jpa_board.user.domain.User;
import com.programmers.jpa_board.user.domain.dto.request.CreateUserRequest;
import com.programmers.jpa_board.user.domain.dto.response.UserResponse;

public class UserConverter {
    public static User toEntity(CreateUserRequest dto) {
        return new User(dto.name(), dto.age(), dto.hobby());
    }

    public static UserResponse toDto(User user) {
        return new UserResponse(user.getId(), user.getName(), user.getAge(), user.getHobby(), user.getPosts(), user.getCreatedAt());
    }
}
