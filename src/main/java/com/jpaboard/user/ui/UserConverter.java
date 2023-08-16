package com.jpaboard.user.ui;

import com.jpaboard.user.domain.User;
import com.jpaboard.user.ui.dto.UserDto;

public class UserConverter {

    public static User convertUser(UserDto.Request response) {
        return new User(response.name(), response.age(), response.hobby());
    }

    public static User convertUser(UserDto.Response response) {
        return new User(response.name(), response.age(), response.hobby());
    }

    public static UserDto.Response convertUserResponse(User user) {
        return new UserDto.Response(user.getName(), user.getAge(), user.getHobby());
    }

    public static UserDto.Request convertUserRequest(User user) {
        return new UserDto.Request(user.getName(), user.getAge(), user.getHobby());
    }

}

