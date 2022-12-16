package com.prgrms.jpa.controller.dto;

import lombok.Getter;

@Getter
public class CreateUserRequest {
    private final String name;
    private final int age;
    private final String hobby;

    public CreateUserRequest(String name, int age, String hobby) {
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }
}
