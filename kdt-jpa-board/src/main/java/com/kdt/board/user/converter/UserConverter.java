package com.kdt.board.user.converter;

import com.kdt.board.user.domain.User;
import com.kdt.board.user.dto.request.UserCreateRequestDto;
import com.kdt.board.user.dto.response.UserResponseDto;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    // entity -> dto
    public UserResponseDto toUserResponseDto (User user) {
        return UserResponseDto.builder()
            .id(user.getId())
            .name(user.getName())
            .age(user.getAge())
            .hobby(user.getHobby())
            .build();
    }

    // dto -> entity
    public User toUser (UserCreateRequestDto requestDto) {
        return new User(requestDto.getName(), requestDto.getAge(), requestDto.getHobby());
    }
}
