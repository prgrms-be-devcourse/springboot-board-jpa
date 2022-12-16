package com.prgrms.jpa.controller;

import com.prgrms.jpa.controller.dto.CreateUserRequest;
import com.prgrms.jpa.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Long> create(CreateUserRequest createUserRequest) {
        long id = userService.create(createUserRequest);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }
}