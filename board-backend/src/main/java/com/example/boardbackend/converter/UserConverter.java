package com.example.boardbackend.converter;

import com.example.boardbackend.domain.User;
import com.example.boardbackend.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    // Entity -> DTO
    public UserDto convertToUserDto(User user){
        return UserDto.builder()
                .id(user.getId())
                .password(user.getPassword())
                .name(user.getName())
                .age(user.getAge())
                .hobby(user.getHobby())
                .createdAt(user.getCreatedAt())
                .build();
    }

    // DTO -> Entity
    public User convertToUserEntity(UserDto userDto){
        User user = new User();
        user.setId(userDto.getId());
        user.setPassword(userDto.getPassword());
        user.setName(userDto.getName());
        user.setAge(userDto.getAge());
        user.setHobby(userDto.getHobby());
        user.setCreatedAt(userDto.getCreatedAt());
        return user;
    }
}
