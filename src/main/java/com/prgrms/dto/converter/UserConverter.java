package com.prgrms.dto.converter;

import com.prgrms.domain.user.User;
import com.prgrms.dto.UserDto.Request;
import com.prgrms.dto.UserDto.Response;

public class UserConverter {

    private UserConverter() {}

    public static Request toUserRequestDto(User user) {

        return new Request(user.getName(), user.getHobby(), user.getAge());
    }

    public static Response toUserResponseDto(User user) {

        return new Response(user.getId(), user.getName(), user.getHobby(), user.getAge());
    }

}
