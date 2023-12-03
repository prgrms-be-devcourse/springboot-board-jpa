package com.example.board.converter;

import com.example.board.auth.dto.request.SignUpRequest;
import com.example.board.domain.Role;
import com.example.board.domain.User;
import com.example.board.dto.response.AuthorResponse;
import com.example.board.dto.response.UserDetailResponse;
import com.example.board.dto.response.UserSummaryResponse;

public class UserConverter {

    public static User toUser(SignUpRequest requestDto) {
        return User.builder()
                .email(requestDto.email())
                .password(requestDto.password())
                .name(requestDto.name())
                .age(requestDto.age())
                .hobby(requestDto.hobby())
                .role(Role.USER)
                .build();
    }

    public static UserSummaryResponse toUserSummaryResponse(User user) {
        return UserSummaryResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .age(user.getAge())
                .hobby(user.getHobby())
                .build();
    }

    public static UserDetailResponse toUserDetailResponse(User user) {
        return UserDetailResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .age(user.getAge())
                .hobby(user.getHobby())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .deletedAt(user.getDeletedAt())
                .build();
    }

    public static AuthorResponse toAuthorResponse(User user) {
        return AuthorResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .build();
    }
}
