package com.devco.jpaproject.controller;

import com.devco.jpaproject.controller.dto.UserRequestDto;
import com.devco.jpaproject.controller.dto.UserResponseDto;
import com.devco.jpaproject.exception.UserNotFoundException;
import com.devco.jpaproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserController {
    private static final String SUCCESS = "SUCCESS";

    private final UserService userService;

    @PostMapping("/users")
    public ApiResponse<Long> insert(@Valid @RequestBody UserRequestDto dto){
        Long id = userService.insert(dto);

        return ApiResponse.created(id);
    }

    @DeleteMapping("/users/{id}")
    public ApiResponse<String> deleteOne(@PathVariable Long id) throws UserNotFoundException {
        userService.deleteOne(id);

        return ApiResponse.ok(SUCCESS);
    }

    @GetMapping("/users/{id}")
    public ApiResponse<UserResponseDto> findById(@PathVariable Long id) throws UserNotFoundException {
        var userResponseDto = userService.findById(id);

        return ApiResponse.ok(userResponseDto);
    }

//    TODO: URI 오류 수정
//    @GetMapping("/users/{name}")
//    public ApiResponse<UserResponseDto> findByName(@PathVariable String name) throws UserNotFoundException {
//        var userResponseDto = userService.findByName(name);
//
//        return ApiResponse.ok(userResponseDto);
//    }
}
