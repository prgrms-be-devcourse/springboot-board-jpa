package com.kdt.simpleboard.user;

import com.kdt.simpleboard.user.domain.User;
import com.kdt.simpleboard.user.dto.UserRequest;
import com.kdt.simpleboard.user.dto.UserResponse;

import static com.kdt.simpleboard.user.dto.UserRequest.*;
import static com.kdt.simpleboard.user.dto.UserResponse.*;

public class UserData {
    private static final String NAME = "hyun";
    private static final String HOBBY = "hiking";
    private static final int AGE = 10;

    public static User user() {
        return User.builder()
                .name(NAME)
                .hobby(HOBBY)
                .age(AGE)
                .build();
    }

    public static CreateUserRequest createUserReq() {
        return CreateUserRequest.builder()
                .name(NAME)
                .hobby(HOBBY)
                .age(AGE)
                .build();
    }
}
