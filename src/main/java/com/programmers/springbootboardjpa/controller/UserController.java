package com.programmers.springbootboardjpa.controller;

import com.programmers.springbootboardjpa.dto.Response;
import com.programmers.springbootboardjpa.dto.user.request.UserCreationRequest;
import com.programmers.springbootboardjpa.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public Response<Map<String, Long>> createUser(@RequestBody UserCreationRequest request) {
        Long saveUserId = userService.saveUser(request);
        return Response.ok(Collections.singletonMap("id", saveUserId) , "User created successfully.");
    }

}
