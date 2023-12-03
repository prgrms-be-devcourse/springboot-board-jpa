package com.programmers.springbootboardjpa.dto;

import com.programmers.springbootboardjpa.entity.User;
import lombok.Getter;

@Getter
public class UserDto {
    private final long userId;
    private final String name;
    private final int age;
    private final String hobby;

    private UserDto(long userId, String name, int age, String hobby) {
        this.userId = userId;
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }

    public static UserDto of(User user){
        return new UserDto(user.getUserId(),
                user.getName(),
                user.getAge(),
                user.getHobby());
    }
}
