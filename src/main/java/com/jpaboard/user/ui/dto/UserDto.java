package com.jpaboard.user.ui.dto;


public sealed interface UserDto permits UserDto.Request, UserDto.Response {

    record Request(String name, int age, String hobby) implements UserDto {
    }

    record Response(String name, int age, String hobby) implements UserDto {
    }

}
