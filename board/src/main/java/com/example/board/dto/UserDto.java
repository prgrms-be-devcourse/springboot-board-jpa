package com.example.board.dto;

import com.example.board.domain.Post;
import com.example.board.domain.User;
import lombok.Builder;

import java.time.LocalDateTime;

public class UserDto {
    @Builder
    public record Request(String name, int age, String hobby) {}

    @Builder
    public record Response(Long userId, String name, int age, String hobby) {}

    public static UserDto.Response toResponse(User user) {
        return Response.builder()
                .userId(user.getId())
                .name(user.getName())
                .age(user.getAge())
                .hobby(user.getHobby())
                .build();
    }
}
