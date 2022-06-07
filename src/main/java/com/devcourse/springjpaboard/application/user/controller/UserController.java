package com.devcourse.springjpaboard.application.user.controller;

import com.devcourse.springjpaboard.application.user.controller.dto.CreateUserRequest;
import com.devcourse.springjpaboard.application.user.controller.dto.UserResponse;
import com.devcourse.springjpaboard.application.user.service.UserService;
import com.devcourse.springjpaboard.core.exception.NotFoundException;
import com.devcourse.springjpaboard.core.util.ApiResponse;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ApiResponse<String> internalServerError(Exception e) {
        return ApiResponse.fail(INTERNAL_SERVER_ERROR, e.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ApiResponse<String> notFoundHandler(NotFoundException e) {
        return ApiResponse.fail(NOT_FOUND, e.getMessage());
    }

    @PostMapping("")
    public ApiResponse<UserResponse> createUser(@RequestBody CreateUserRequest createUserRequest) {
        UserResponse user = userService.save(createUserRequest);
        return ApiResponse.ok(OK, user);
    }

    @GetMapping("/{id}")
    public ApiResponse<UserResponse> getUserById(@PathVariable Long id) {
        UserResponse user = userService.findById(id);
        return ApiResponse.ok(OK, user);
    }

}
