package com.programmers.board.core.user.application.mapper;

import com.programmers.board.core.user.application.dto.UserDto;
import com.programmers.board.core.user.domain.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User convertToEntity(UserDto requestUser){
        return User.builder()
                .name(requestUser.getName())
                .age(requestUser.getAge())
                .hobby(requestUser.getHobby())
                .build();
    }

    public UserDto convertToDto(User user){
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .age(user.getAge())
                .hobby(user.getHobby())
                .build();
    }
}
