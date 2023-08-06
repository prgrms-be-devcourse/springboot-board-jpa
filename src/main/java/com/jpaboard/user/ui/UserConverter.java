package com.jpaboard.user.ui;

import com.jpaboard.user.domain.User;
import com.jpaboard.user.ui.dto.UserResponse;

public class UserConverter {
    public static User convertUser(UserResponse userResponse) {
        return User.builder()
                .name(userResponse.name())
                .age(userResponse.age())
                .hobby(userResponse.hobby())
                .build();
    }

    public static UserResponse convertUserDto(User user) {
        return UserResponse.builder()
                .name(user.getName())
                .age(user.getAge())
                .hobby(user.getHobby())
                .build();
    }

}

