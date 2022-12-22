package com.prgrms.jpa.utils;

import com.prgrms.jpa.controller.dto.user.CreateUserRequest;
import com.prgrms.jpa.controller.dto.user.CreateUserResponse;
import com.prgrms.jpa.controller.dto.user.GetByIdUserResponse;
import com.prgrms.jpa.domain.User;

public class UserEntityDtoMapper {

    private UserEntityDtoMapper() {
    }

    public static CreateUserResponse toUserIdDto(long id) {
        return new CreateUserResponse(id);
    }

    public static GetByIdUserResponse toUserDto(User user) {
        return GetByIdUserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .age(user.getAge())
                .hobby(user.getHobby())
                .build();
    }

    public static User toUser(CreateUserRequest createUserRequest) {
        return User.builder()
                .name(createUserRequest.getName())
                .age(createUserRequest.getAge())
                .hobby(createUserRequest.getHobby())
                .build();
    }
}
