package com.example.jpaboard.user.converter;

import com.example.jpaboard.user.domain.User;
import com.example.jpaboard.user.dto.UserDto;
import java.time.LocalDateTime;

public class UserConverter {

    public static UserDto convertUserDto(User user) {
        Long id = user.getId();
        String name = user.getName();
        String hobby = user.getHobby();
        return new UserDto(id, name, hobby);
    }

}
