package com.jpaboard.user.ui.dto;

import static com.jpaboard.user.ui.dto.UserDto.Response;
import static com.jpaboard.user.ui.dto.UserDto.Request;

public sealed interface UserDto permits Request, Response {

    record Request(String name, int age, String hobby) implements UserDto {
    }

    record Response(String name, int age, String hobby) implements UserDto {
    }

}
