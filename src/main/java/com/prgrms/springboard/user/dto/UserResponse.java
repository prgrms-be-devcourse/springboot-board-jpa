package com.prgrms.springboard.user.dto;

import com.prgrms.springboard.user.domain.User;

import lombok.Getter;

@Getter
public class UserResponse {

    private Long id;
    private String name;
    private int age;
    private String hobby;

    protected UserResponse() {
    }

    public UserResponse(Long id, String name, int age, String hobby) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }

    public static UserResponse from(User user) {
        return new UserResponse(
            user.getId(),
            user.getName().getName(),
            user.getAge().getAge(),
            user.getHobby().getHobby()
        );
    }

}
