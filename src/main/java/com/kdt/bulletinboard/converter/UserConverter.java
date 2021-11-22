package com.kdt.bulletinboard.converter;

import com.kdt.bulletinboard.dto.UserDto;
import com.kdt.bulletinboard.entity.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UserConverter {

    public User convertToUser(UserDto userDto) {
        return new User(userDto.getName(), userDto.getHobby(), LocalDateTime.now().toString());
    }

    public UserDto convertToUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getUserName())
                .hobby(user.getHobby())
                .build();
    }

}
