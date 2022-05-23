package com.devcourse.springjpaboard.user.controller.stub;

import com.devcourse.springjpaboard.user.controller.dto.CreateUserRequest;
import com.devcourse.springjpaboard.user.controller.dto.UserResponse;

public class UserStubs {

    private UserStubs() {}

    public static CreateUserRequest createUserRequest() {
        return new CreateUserRequest("yongcheol", 11, "coding");
    }

    public static UserResponse userResponse() {
        return new UserResponse(1L, "yongcheol", 11, "coding");
    }
}
