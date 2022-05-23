package com.devcourse.springjpaboard.user.controller;

import com.devcourse.springjpaboard.exception.NotFoundException;
import com.devcourse.springjpaboard.user.controller.dto.CreateUserRequest;
import com.devcourse.springjpaboard.user.controller.dto.UserResponse;
import com.devcourse.springjpaboard.user.service.UserService;
import com.devcourse.springjpaboard.util.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/user")
public class UserControllerImpl implements UserController {

    private final UserService userService;

    public UserControllerImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    @ExceptionHandler(Exception.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ApiResponse<String> internalServerError(Exception e) {
        return ApiResponse.fail(INTERNAL_SERVER_ERROR, e.getMessage());
    }

    @Override
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ApiResponse<String> notFoundHandler(NotFoundException e) {
        return ApiResponse.fail(NOT_FOUND, e.getMessage());
    }

    @Override
    @PostMapping("")
    public ApiResponse<UserResponse> createUser(@RequestBody CreateUserRequest createUserRequest) {
        UserResponse user = userService.save(createUserRequest);
        return ApiResponse.ok(OK, user);
    }

    @Override
    @GetMapping("/{id}")
    public ApiResponse<UserResponse> getUserById(@PathVariable Long id) {
        UserResponse user = userService.findById(id);
        return ApiResponse.ok(OK, user);
    }

}
