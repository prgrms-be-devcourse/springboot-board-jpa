package com.programmers.jpa_board.global.converter;

import com.programmers.jpa_board.user.domain.User;
import com.programmers.jpa_board.user.domain.dto.request.CreateUserRequest;
import com.programmers.jpa_board.user.domain.dto.response.CreateUserResponse;
import org.springframework.stereotype.Component;

@Component
public class BoardConverter {
    public User toUser(CreateUserRequest dto) {
        return User.builder()
                .name(dto.getName())
                .age(dto.getAge())
                .hobby(dto.getHobby())
                .build();
    }

    public CreateUserResponse toDto(User user) {
        return CreateUserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .age(user.getAge())
                .hobby(user.getHobby())
                .posts(user.getPosts())
                .createAt(user.getCreatedAt())
                .build();
    }
}
