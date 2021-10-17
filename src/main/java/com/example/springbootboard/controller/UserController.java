package com.example.springbootboard.controller;

import com.example.springbootboard.controller.api.ApiResponse;
import com.example.springbootboard.dto.UserRequestDto;
import com.example.springbootboard.dto.UserResponseDto;
import com.example.springbootboard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    public UserService userService;

    @PostMapping("/users")
    public ApiResponse<Long> save(@RequestBody UserRequestDto userRequestDto) {
        Long id = userService.insert(userRequestDto);
        return ApiResponse.ok(id);
    }

    @GetMapping("/users/{id}")
    public ApiResponse<UserResponseDto> getOne(@PathVariable(value = "id") Long id) {
        UserResponseDto userResponseDto = userService.findById(id);
        return ApiResponse.ok(userResponseDto);
    }

    @GetMapping("/users")
    public ApiResponse<List<UserResponseDto>> getAll() {
        List<UserResponseDto> all = userService.findAll();
        return ApiResponse.ok(all);
    }

    @PatchMapping("/users/{id}")
    public ApiResponse<UserResponseDto> update(
            @PathVariable(value = "id") Long id,
            @RequestBody UserRequestDto userRequestDto
    ) {
        UserResponseDto userResponseDto = userService.update(id, userRequestDto);
        return ApiResponse.ok(userResponseDto);
    }

    @DeleteMapping("/users/{id}")
    public ApiResponse<String> delete(
            @PathVariable(value = "id") Long id
    ) {
        userService.delete(id);
        return ApiResponse.ok("유저 삭제 성공");
    }
}
