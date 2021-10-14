package com.example.jpaboard.controller;

import com.example.jpaboard.domain.User;
import com.example.jpaboard.dto.ApiResponse;
import com.example.jpaboard.dto.UserRequest;
import com.example.jpaboard.dto.UserResponse;
import com.example.jpaboard.exception.UserNotFoundException;
import com.example.jpaboard.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @PostMapping
    public ApiResponse<UserResponse> join(@RequestBody UserRequest userRequest) {
        User user = new User(userRequest.getName(), userRequest.getHobby());
        userRepository.save(user);
        return ApiResponse.ok(new UserResponse(user.getId(), user.getName(), user.getHobby()));
    }

    @GetMapping("{userId}")
    public ApiResponse<UserResponse> getUser(@PathVariable Long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        return ApiResponse.ok(new UserResponse(user.getId(), user.getName(), user.getHobby()));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void userNotFoundHandler(UserNotFoundException exception) {}
}
