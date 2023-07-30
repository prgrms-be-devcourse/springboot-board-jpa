package com.programmers.user.application.converter;

import com.programmers.user.domain.User;
import com.programmers.user.ui.dto.UserDto;
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
