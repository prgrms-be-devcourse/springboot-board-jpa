package com.example.boardbackend.dto.converter;

import com.example.boardbackend.domain.User;
import com.example.boardbackend.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {
    // Entity -> DTO
    public UserDto convertToDto(User user){
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .name(user.getName())
                .age(user.getAge())
                .hobby(user.getHobby())
                .createdAt(user.getCreatedAt())
                .build();
    }

    // DTO -> Entity
    public User convertToEntity(UserDto userDto){
        User user = new User();
        user.setId(userDto.getId());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setName(userDto.getName());
        user.setAge(userDto.getAge());
        user.setHobby(userDto.getHobby());
        user.setCreatedAt(userDto.getCreatedAt());
        return user;
    }
}
