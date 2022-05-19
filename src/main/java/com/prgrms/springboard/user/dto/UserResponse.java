package com.prgrms.springboard.user.dto;

import com.prgrms.springboard.user.domain.User;

import lombok.Getter;

@Getter
public class UserResponse {

    private final Long id;
    private final String name;
    private final int age;
    private final String hobby;

    public UserResponse(Long id, String name, int age, String hobby) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }

    public static UserResponse from(User user) {
        return new UserResponse(
            user.getId(),
            user.getName().getValue(),
            user.getAge().getValue(),
            user.getHobby().getValue()
        );
    }

}
