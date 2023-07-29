package com.jpaboard.domain.user;

import com.jpaboard.domain.user.dto.request.UserCreationRequest;
import com.jpaboard.domain.user.dto.request.UserUpdateRequest;
import com.jpaboard.domain.user.dto.response.UserResponse;

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
    public static User convertRequestToEntity(UserUpdateRequest request) {
        return User.builder()
                .name(request.name())
                .age(request.age())
                .hobby(request.hobby())
                .build();
    }
}
