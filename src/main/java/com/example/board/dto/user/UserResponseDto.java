package com.example.board.dto.user;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserResponseDto {
    private String name;

    private String email;

    private int age;

    private String hobby;

    @Builder
    public UserResponseDto(String name, String email, int age, String hobby) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.hobby = hobby;
    }
}
