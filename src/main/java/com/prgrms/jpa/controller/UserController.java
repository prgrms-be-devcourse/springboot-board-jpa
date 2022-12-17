package com.prgrms.jpa.controller;

import com.prgrms.jpa.controller.dto.user.CreateUserRequest;
import com.prgrms.jpa.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Map<String, Long>> create(@RequestBody @Valid CreateUserRequest createUserRequest) {
        long id = userService.create(createUserRequest);
        return new ResponseEntity<>(Map.of("id", id), HttpStatus.CREATED);
    }
}