package com.devcourse.springjpaboard.application.user.service;

import com.devcourse.springjpaboard.application.user.controller.dto.CreateUserRequest;
import com.devcourse.springjpaboard.application.user.controller.dto.UserResponse;

public interface UserService {

  UserResponse save(CreateUserRequest createUserRequest);

  UserResponse findById(Long id);
}
