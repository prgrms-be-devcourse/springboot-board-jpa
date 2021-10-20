package com.misson.jpa_board.controller;

import com.misson.jpa_board.ApiResponse;
import com.misson.jpa_board.dto.UserDto;
import com.misson.jpa_board.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public ApiResponse<Long> save(@RequestBody UserDto userDto) {
        log.info("회원 추가");
        UserDto insertedUser = userService.insert(userDto);
        return ApiResponse.ok(insertedUser.getId());
    }
}
