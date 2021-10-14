package com.example.jpaboard.user.dto;

import lombok.Getter;

@Getter
public class UserRequest {

    private String name;
    private String hobby;

    public UserRequest(String name, String hobby) {
        this.name = name;
        this.hobby = hobby;
    }
}
