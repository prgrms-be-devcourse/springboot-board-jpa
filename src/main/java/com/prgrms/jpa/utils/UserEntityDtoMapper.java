package com.prgrms.jpa.utils;

import com.prgrms.jpa.controller.dto.user.CreateUserRequest;
import com.prgrms.jpa.controller.dto.user.CreateUserResponse;
import com.prgrms.jpa.domain.User;

public class UserEntityDtoMapper {

    private UserEntityDtoMapper() {
    }

    public static CreateUserResponse toUserIdDto(long id) {
        return new CreateUserResponse(id);
    }

    public static User toUser(CreateUserRequest createUserRequest) {
        return User.builder()
                .name(createUserRequest.getName())
                .age(createUserRequest.getAge())
                .hobby(createUserRequest.getHobby())
                .build();
    }
}
