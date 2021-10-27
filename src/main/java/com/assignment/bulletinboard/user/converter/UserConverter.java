package com.assignment.bulletinboard.user.converter;

import com.assignment.bulletinboard.user.User;
import com.assignment.bulletinboard.user.dto.UserDto;

public class UserConverter {

    public User convertToUser(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .age(userDto.getAge())
                .name(userDto.getName())
                .hobby(userDto.getHobby())
                .build();
    }

    public UserDto convertToUserDto (User user) {
        return UserDto.builder()
                .id(user.getId())
                .age(user.getAge())
                .name(user.getName())
                .hobby(user.getHobby())
                .build();
    }
}
