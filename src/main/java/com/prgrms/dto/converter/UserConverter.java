package com.prgrms.dto.converter;

import com.prgrms.domain.user.User;
import com.prgrms.dto.UserDto.Response;

public class UserConverter {

    private UserConverter() {}

    public static Response toUserResponseDto(User user) {

        return new Response(user.getId(), user.getName(), user.getHobby(), user.getAge());
    }

}
