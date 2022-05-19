package com.kdt.jpaboard.domain.board.user.controller;

import com.kdt.jpaboard.domain.board.ApiResponse;
import com.kdt.jpaboard.domain.board.user.dto.CreateUserDto;
import com.kdt.jpaboard.domain.board.user.dto.UserDto;
import com.kdt.jpaboard.domain.board.user.service.UserService;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ExceptionHandler(NotFoundException.class)
    public ApiResponse<String> notFoundHandler (NotFoundException e) {
        return ApiResponse.fail(HttpStatus.NOT_FOUND.value(), e.getMessage());
    }

    @ExceptionHandler(HttpServerErrorException.class)
    public ApiResponse<String> internalServerException (HttpServerErrorException e) {
        return ApiResponse.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }

    @GetMapping("")
    public ApiResponse<Page<UserDto>> getAll(Pageable pageable) {
        Page<UserDto> users = userService.findAll(pageable);
        return ApiResponse.ok(users);
    }

    @GetMapping("/{userId}")
    public ApiResponse<UserDto> getOne(@PathVariable Long userId) throws NotFoundException {
        UserDto user = userService.findById(userId);
        return ApiResponse.ok(user);
    }

    @PostMapping("")
    public ApiResponse<Long> save(@RequestBody CreateUserDto userDto) {
        Long save = userService.save(userDto);
        return ApiResponse.ok(save);
    }

    @PutMapping("/{userId}")
    public ApiResponse<Long> update(@PathVariable Long userId,
                                    @RequestBody CreateUserDto updatePostDto) throws NotFoundException {
        Long update = userService.update(userId, updatePostDto);
        return ApiResponse.ok(update);
    }

}
