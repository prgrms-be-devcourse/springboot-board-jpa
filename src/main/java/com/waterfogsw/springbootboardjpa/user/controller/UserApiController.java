package com.waterfogsw.springbootboardjpa.user.controller;

import com.waterfogsw.springbootboardjpa.user.controller.dto.UserAddRequest;
import com.waterfogsw.springbootboardjpa.user.service.UserService;
import com.waterfogsw.springbootboardjpa.user.util.UserConverter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/users")
public class UserApiController {

    private final UserService userService;
    private final UserConverter userConverter;

    public UserApiController(UserService userService, UserConverter userConverter) {
        this.userService = userService;
        this.userConverter = userConverter;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addUser(@RequestBody @Valid UserAddRequest userAddRequest) {
        final var user = userConverter.toEntity(userAddRequest);
        userService.addUser(user);
    }
}
