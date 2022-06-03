package com.programmers.springbootboardjpa.controller;

import com.programmers.springbootboardjpa.dto.Response;
import com.programmers.springbootboardjpa.dto.user.request.UserCreationRequest;
import com.programmers.springbootboardjpa.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Response<Map<String, Long>> createUser(@RequestBody UserCreationRequest request) {
        Long saveUserId = userService.saveUser(request);
        return Response.ok(HttpStatus.CREATED, Collections.singletonMap("id", saveUserId) , "User created successfully.");
    }

}
