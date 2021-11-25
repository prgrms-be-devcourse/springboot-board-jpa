package com.maenguin.kdtbbs.dto;

import lombok.Getter;

@Getter
public class UserDto {

    private String name;

    private String hobby;

    public UserDto(String name, String hobby) {
        this.name = name;
        this.hobby = hobby;
    }
}
