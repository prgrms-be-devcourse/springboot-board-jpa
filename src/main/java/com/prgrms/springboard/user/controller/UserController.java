package com.prgrms.springboard.user.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.prgrms.springboard.global.common.ApiResponse;
import com.prgrms.springboard.user.dto.CreateUserRequest;
import com.prgrms.springboard.user.dto.UserResponse;
import com.prgrms.springboard.user.service.UserService;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public ResponseEntity<ApiResponse<Long>> createUser(@Valid @RequestBody CreateUserRequest userRequest) {
        Long id = userService.join(userRequest);
        return ResponseEntity.created(URI.create("/api/v1/users/" + id)).body(ApiResponse.created(id));
    }
    
    @GetMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<UserResponse> showUser(@PathVariable Long id) {
        UserResponse user = userService.findOne(id);
        return ApiResponse.ok(user);
    }
}
