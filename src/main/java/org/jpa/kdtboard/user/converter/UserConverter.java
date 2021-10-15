package org.jpa.kdtboard.user.converter;

import org.jpa.kdtboard.domain.board.User;
import org.jpa.kdtboard.user.dto.UserDto;
import org.springframework.stereotype.Component;

/**
 * Created by yunyun on 2021/10/14.
 */

@Component
public class UserConverter {

    public UserDto convertDto(User user){
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .createdBy(user.getCreatedBy())
                .age(user.getAge())
                .hobby(user.getHobby())
                .build();
    }

    public User convertEntity(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setCreatedBy(userDto.getCreatedBy());
        user.setAge(userDto.getAge());
        user.setHobby(userDto.getHobby());
        return user;
    }
}
