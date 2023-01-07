package com.example.bulletin_board_jpa.user.dto;

import lombok.Getter;

@Getter
public class UserDto {
    private String name;
    private int age;
    private String hobby;

    public UserDto(String name, int age, String hobby) {
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }
}
