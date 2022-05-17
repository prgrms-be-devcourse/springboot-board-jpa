package com.springboard.user.util;

import com.springboard.user.dto.CreateUserRequest;
import com.springboard.user.dto.CreateUserResponse;
import com.springboard.user.dto.FindUserResponse;
import com.springboard.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {
    public static User getUserFrom(CreateUserRequest request){
        return new User(request.name(), request.age(), request.hobby());
    }

    public static CreateUserResponse getCreateResponseFrom(User user) {
        return new CreateUserResponse(user.getId(), user.getName(), user.getAge(), user.getHobby(),
            user.getCreatedAt());
    }

    public static FindUserResponse getFindResponseFrom(User user) {
        return new FindUserResponse(user.getId(), user.getName(), user.getAge(), user.getHobby(),
            user.getCreatedAt(), user.getUpdatedAt());
    }
}
