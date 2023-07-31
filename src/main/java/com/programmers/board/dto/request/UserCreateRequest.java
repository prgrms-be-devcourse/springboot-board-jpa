package com.programmers.board.dto.request;

import lombok.Getter;

@Getter
public class UserCreateRequest {
    private final String name;
    private final int age;
    private final String hobby;

    public UserCreateRequest(String name, int age, String hobby) {
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }
}
