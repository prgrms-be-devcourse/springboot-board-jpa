package com.springboard.user.util;

import com.springboard.user.dto.*;
import com.springboard.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {
    public User getUserFrom(UserRequest request) {
        return new User(request.name(), request.age(), request.hobby());
    }

    public UserResponse getResponseFrom(User user) {
        return new UserResponse(user.getId(), user.getName(), user.getAge(), user.getHobby(),
            user.getCreatedAt(), user.getUpdatedAt());
    }
}
