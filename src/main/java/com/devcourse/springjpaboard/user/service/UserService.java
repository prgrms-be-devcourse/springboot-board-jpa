package com.devcourse.springjpaboard.user.service;

import com.devcourse.springjpaboard.user.controller.dto.CreateUserRequest;
import com.devcourse.springjpaboard.user.controller.dto.UserResponse;

public interface UserService {
    UserResponse save(CreateUserRequest createUserRequest);

    UserResponse findById(Long id);
}
