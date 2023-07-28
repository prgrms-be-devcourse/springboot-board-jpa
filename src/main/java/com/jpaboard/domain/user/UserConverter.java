package com.jpaboard.domain.user;

import com.jpaboard.domain.user.dto.UserCreationRequest;
import com.jpaboard.domain.user.dto.UserResponse;

public final class UserConverter {
    private UserConverter() {

    }

    public static UserResponse convertEntityToResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .age(user.getAge())
                .hobby(user.getHobby())
                .build();
    }

    public static User convertRequestToEntity(UserCreationRequest request) {
        return User.builder()
                .name(request.name())
                .age(request.age())
                .hobby(request.hobby())
                .build();
    }
}
