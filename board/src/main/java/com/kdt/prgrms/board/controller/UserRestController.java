package com.kdt.prgrms.board.controller;

import com.kdt.prgrms.board.dto.UserAddRequest;
import com.kdt.prgrms.board.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/users")
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {

        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addUser(@RequestBody @Valid UserAddRequest userAddRequest) {

        userService.addUser(userAddRequest.toEntity());
    }
}
