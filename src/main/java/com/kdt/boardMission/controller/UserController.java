package com.kdt.boardMission.controller;

import com.kdt.boardMission.ApiResponse;
import com.kdt.boardMission.dto.UserDto;
import com.kdt.boardMission.service.UserService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @ExceptionHandler(NotFoundException.class)
    public ApiResponse<String> notFoundHandler(NotFoundException e) {
        return ApiResponse.fail(404, e.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    public ApiResponse<String> interalServerErrorHandler(Exception e) {
        return ApiResponse.fail(500, e.getMessage());
    }

    @PostMapping("/user")
    public ApiResponse<Long> enrollUser(@RequestBody UserDto userDto) {
        long userId = userService.saveUser(userDto);
        return ApiResponse.ok(userId);
    }

    @GetMapping("/user")
    public ApiResponse<Page<UserDto>> getUserByTitle(@RequestParam("name") Optional<String> name, Pageable pageable) {
        if (name.isEmpty()) {
            Page<UserDto> all = userService.findAll(pageable);
            return ApiResponse.ok(all);
        }
        Page<UserDto> userByName = userService.findUserByName(name.get(), pageable);
        return ApiResponse.ok(userByName);
    }

    @GetMapping("/user/{userId}")
    public ApiResponse<UserDto> getOne(@PathVariable("userId") Long userId) throws NotFoundException {
        UserDto userById = userService.findUserById(userId);
        return ApiResponse.ok(userById);
    }

    @PutMapping("/user/edit")
    public ApiResponse<UserDto> editHobby(@RequestBody UserDto userDto) throws NotFoundException {
        userService.updateUserHobby(userDto);
        return ApiResponse.ok(userDto);
    }
}
