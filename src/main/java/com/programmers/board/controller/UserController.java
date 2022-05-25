package com.programmers.board.controller;

import com.programmers.board.dto.UserRequestDto;
import com.programmers.board.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PostMapping("")
    public ApiResponse<Long> createUser(@RequestBody UserRequestDto userRequestDto) {
        Long id = userService.save(userRequestDto);
        return ApiResponse.ok(id);
    }
}
