package com.devcourse.springjpaboard.user.controller;

import com.devcourse.springjpaboard.exception.NotFoundException;
import com.devcourse.springjpaboard.user.controller.dto.CreateUserRequest;
import com.devcourse.springjpaboard.user.controller.dto.UserResponse;
import com.devcourse.springjpaboard.util.ApiResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserController {

    ApiResponse<String> internalServerError(Exception e);

    ApiResponse<String> notFoundHandler(NotFoundException e);

    ApiResponse<UserResponse> createUser(CreateUserRequest createUserRequest);

    ApiResponse<UserResponse> getUserById(Long id);


}
