package com.example.board.dto;

import com.example.board.domain.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

public class UserDto {
    @Builder
    public record Request(@NotEmpty String name, @NotNull Integer age, @NotEmpty String hobby) {}

    @Builder
    public record Response(Long userId, String name, int age, String hobby) {}
    public static User toEntity(UserDto.Request request) {
        return User.builder()
                .name(request.name())
                .age(request.age())
                .hobby(request.hobby())
                .build();
    }
    public static UserDto.Response toResponse(User user) {
        return Response.builder()
                .userId(user.getId())
                .name(user.getName())
                .age(user.getAge())
                .hobby(user.getHobby())
                .build();
    }
}
