package com.prgrms.jpa.utils;

import com.prgrms.jpa.controller.dto.CreateUserRequest;
import com.prgrms.jpa.domain.User;

public class ToEntityMapper {

    private ToEntityMapper() {
    }

    public static User toUser(CreateUserRequest createUserRequest) {
        return User.builder()
                .name(createUserRequest.getName())
                .age(createUserRequest.getAge())
                .hobby(createUserRequest.getHobby())
                .build();
    }
}
