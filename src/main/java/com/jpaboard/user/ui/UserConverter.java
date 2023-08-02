package com.jpaboard.user.ui;

import com.jpaboard.user.domain.User;
import com.jpaboard.user.ui.dto.UserDto;

public class UserConverter {
    public static User convertUser(UserDto userDto) {
        return User.builder()
                .name(userDto.name())
                .age(userDto.age())
                .hobby(userDto.hobby())
                .build();
    }

    public static UserDto convertUserDto(User user) {
        return UserDto.builder()
                .name(user.getName())
                .age(user.getAge())
                .hobby(user.getHobby())
                .build();
    }

}

