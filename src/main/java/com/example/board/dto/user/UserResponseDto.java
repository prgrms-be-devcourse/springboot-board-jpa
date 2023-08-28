package com.example.board.dto.user;

import com.example.board.domain.entity.User;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserResponseDto {
    private String name;

    private String email;

    private int age;

    private String hobby;

    public static UserResponseDto from(User user) {
        return UserResponseDto.builder()
                .name(user.getName())
                .email(user.getEmail())
                .age(user.getAge())
                .hobby(user.getHobby())
                .build();
    }
}
