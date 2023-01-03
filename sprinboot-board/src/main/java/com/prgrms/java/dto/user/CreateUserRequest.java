package com.prgrms.java.dto.user;

import com.prgrms.java.domain.HobbyType;
import com.prgrms.java.domain.User;

public record CreateUserRequest(UserLoginInfo userLoginInfo, UserSideInfo userSideInfo) {
    public User toEntity() {
        return new User(
                userSideInfo.name(),
                userLoginInfo.email(),
                userLoginInfo.password(),
                userSideInfo.age(),
                userSideInfo.hobby()
        );
    }
}
