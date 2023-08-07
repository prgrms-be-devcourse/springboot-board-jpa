package com.prgms.jpaBoard.domain.user.application;

import com.prgms.jpaBoard.domain.user.User;
import com.prgms.jpaBoard.domain.user.presentation.dto.UserSaveRequest;

public final class UserMapper {

    private UserMapper() {

    }

    public static User from(UserSaveRequest userSaveRequest) {

        return new User(
                userSaveRequest.name(),
                userSaveRequest.age(),
                userSaveRequest.hobbyType()
        );
    }
}
