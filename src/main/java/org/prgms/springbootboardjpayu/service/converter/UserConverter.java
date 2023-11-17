package org.prgms.springbootboardjpayu.service.converter;

import org.prgms.springbootboardjpayu.domain.User;
import org.prgms.springbootboardjpayu.dto.request.CreateUserRequest;
import org.prgms.springbootboardjpayu.dto.response.UserResponse;

public final class UserConverter {

    private UserConverter() {}

    public static User toUser(CreateUserRequest request) {
        return User.builder()
                .name(request.name())
                .age(request.age())
                .hobby(request.hobby())
                .build();
    }

    public static UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .age(user.getAge())
                .hobby(user.getHobby())
                .createdAt(user.getCratedAt())
                .build();
    }

}
