package com.kdt.jpaboard.domain.board.user.converter;

import com.kdt.jpaboard.domain.board.user.User;
import com.kdt.jpaboard.domain.board.user.dto.CreateUserDto;
import com.kdt.jpaboard.domain.board.user.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    public UserDto convertUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .age(user.getAge())
                .hobby(user.getHobby())
                .build();
    }

    public User convertUser(UserDto user) {
        return User.builder()
                .id(user.getId())
                .name(user.getName())
                .age(user.getAge())
                .hobby(user.getHobby())
                .build();
    }

    public User convertNewUser(CreateUserDto user) {
        return User.builder()
                .name(user.getName())
                .age(user.getAge())
                .hobby(user.getHobby())
                .build();

    }

}
