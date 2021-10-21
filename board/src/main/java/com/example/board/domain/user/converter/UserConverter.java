package com.example.board.domain.user.converter;

import com.example.board.domain.user.domain.User;
import com.example.board.domain.user.dto.UserDto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UserConverter {

    // Dto -> Entity
    public User convertUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setAge(userDto.getAge());
        user.setHobby(userDto.getHobby());
        user.setCreatedAt(LocalDateTime.now());

        return user;
    }

    // Entity -> Dto
    public UserDto convertUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .age(user.getAge())
                .hobby(user.getHobby())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
