package com.kdt.board.user.controller;

import com.kdt.board.common.dto.ApiResponse;
import com.kdt.board.user.dto.request.UserCreateRequestDto;
import com.kdt.board.user.dto.response.UserResponseDto;
import com.kdt.board.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;

    @GetMapping("/users/{id}")
    public ApiResponse<UserResponseDto> getOne(@PathVariable("id") Long userId) {
        UserResponseDto userResponseDto = userService.findOne(userId);
        return ApiResponse.ok(userResponseDto);
    }

    @PostMapping("/users")
    public ApiResponse<Long> saveUser(@RequestBody UserCreateRequestDto userCreateRequestDto) {
        Long userId = userService.save(userCreateRequestDto);
        return ApiResponse.ok(userId);
    }
}
