package com.kdt.simpleboard.user.dto;

import com.kdt.simpleboard.user.domain.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.kdt.simpleboard.user.dto.UserResponse.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMapper {
    public static CreateUserResponse toCreateUserRes(User user){
        return new CreateUserResponse(user.getId());
    }

    public static User toUser(UserRequest.CreateUserRequest request){
        return User.builder()
                .name(request.name())
                .hobby(request.hobby())
                .age(request.age()).build();
    }
}
