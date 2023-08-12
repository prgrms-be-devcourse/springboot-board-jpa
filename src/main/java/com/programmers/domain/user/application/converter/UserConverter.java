package com.programmers.domain.user.application.converter;

import com.programmers.domain.user.entity.User;
import com.programmers.domain.user.ui.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    public User convertUser(UserDto userDto) {
        return new User(userDto.name(), userDto.age(), userDto.hobby());
    }

    public UserDto convertUserDto(User user) {
        return new UserDto(user.getName(), user.getAge(), user.getHobby());
    }
}
