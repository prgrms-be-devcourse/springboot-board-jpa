package com.programmers.board.dto;

import com.programmers.board.domain.User;
import lombok.Getter;

@Getter
public class UserDto {
    private final Long userId;
    private final String name;
    private final int age;
    private final String hobby;

    UserDto(Long userId, String name, int age, String hobby) {
        this.userId = userId;
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }

    public static UserDto from(User user) {
        return new UserDto(user.getId(), user.getName(), user.getAge(), user.getHobby());
    }
}
