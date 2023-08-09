package com.jpaboard.user.ui;

import com.jpaboard.user.domain.User;
import com.jpaboard.user.ui.dto.UserDto;

public class UserConverter {

    public static User convertUser(UserDto.Request response) {
        return User.builder()
                .name(response.name())
                .age(response.age())
                .hobby(response.hobby())
                .build();
    }

    public static User convertUser(UserDto.Response response) {
        return User.builder()
                .name(response.name())
                .age(response.age())
                .hobby(response.hobby())
                .build();
    }

    public static UserDto.Response convertUserResponse(User user) {
        return UserDto.Response.builder()
                .name(user.getName())
                .age(user.getAge())
                .hobby(user.getHobby())
                .build();
    }

    public static UserDto.Request convertUserRequest(User user) {
        return UserDto.Request.builder()
                .name(user.getName())
                .age(user.getAge())
                .hobby(user.getHobby())
                .build();
    }

}

