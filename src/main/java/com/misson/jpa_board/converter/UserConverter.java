package com.misson.jpa_board.converter;

import com.misson.jpa_board.domain.User;
import com.misson.jpa_board.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {
    public User convertUser(UserDto userDto) {
        return User.builder()
                .age(userDto.getAge())
                .hobby(userDto.getHobby())
                .name(userDto.getName())
                .build();
    }

    public UserDto convertUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .age(user.getAge())
                .hobby(user.getHobby())
                .name(user.getName())
                .build();
    }
}
