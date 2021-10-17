package org.programmers.project_board.converter;

import org.programmers.project_board.dto.UserDto;
import org.programmers.project_board.domain.User;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    // dto -> entity
    public User convertUser(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setAge(userDto.getAge());
        user.setHobby(userDto.getHobby());

        return user;
    }

    // entity -> dto
    public UserDto convertUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .age(user.getAge())
                .hobby(user.getHobby())
                .build();
    }

}
