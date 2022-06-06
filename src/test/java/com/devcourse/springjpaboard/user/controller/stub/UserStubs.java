package com.devcourse.springjpaboard.user.controller.stub;

import com.devcourse.springjpaboard.model.user.User;
import com.devcourse.springjpaboard.user.controller.dto.CreateUserRequest;
import com.devcourse.springjpaboard.user.controller.dto.UserResponse;

import java.time.LocalDateTime;

public class UserStubs {

    private UserStubs() {}

    public static CreateUserRequest createUserRequest() {
        return new CreateUserRequest("yongcheol", 11, "coding");
    }

    public static UserResponse userResponse() {
        return new UserResponse(1L, "yongcheol", 11, "coding");
    }

    public static User findUser(Long userId) {
        User user = new User();
        user.setId(1L);
        user.setName("yongcheol");
        user.setAge(22);
        return user;
    }
}
