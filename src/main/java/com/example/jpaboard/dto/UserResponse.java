package com.example.jpaboard.dto;

import lombok.Getter;

@Getter
public class UserResponse {

    private Long id;
    private String name;
    private String hobby;

    public UserResponse(Long id, String name, String hobby) {
        this.id = id;
        this.name = name;
        this.hobby = hobby;
    }
}
