package com.kdt.springbootboard.controller;

import com.kdt.springbootboard.dto.user.UserCreateRequest;
import com.kdt.springbootboard.dto.user.UserResponse;
import com.kdt.springbootboard.dto.user.UserUpdateRequest;
import com.kdt.springbootboard.service.UserService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // Todo : ResponseEntity 찾아보기
    @ExceptionHandler
    private ApiResponse<String> exceptionHandle(Exception exception) {
        return ApiResponse.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    private ApiResponse<String> notFoundHandle(NotFoundException exception) {
        return ApiResponse.fail(HttpStatus.NOT_FOUND.value(), exception.getMessage());
    }

    @PostMapping("/api/v1/users")
    public ApiResponse<Long> insertUser(@RequestBody UserCreateRequest request) {
        Long id = userService.insert(request);
        return ApiResponse.ok(id);
    }

    @GetMapping("/api/v1/users/{id}")
    public ApiResponse<UserResponse> getUser(@PathVariable Long id) throws NotFoundException {
        return ApiResponse.ok(userService.findById(id));
    }

    @GetMapping("/api/v1/users")
    public ApiResponse<Page<UserResponse>> getAllUser(Pageable pageable) {
        return ApiResponse.ok(userService.findAll(pageable));
    }

    @PutMapping("/api/v1/users")
    public ApiResponse<UserResponse> updateUser(@RequestBody UserUpdateRequest request) throws NotFoundException {
        return ApiResponse.ok(userService.update(request));
    }

    @DeleteMapping("/api/v1/users/{id}")
    public ApiResponse<Long> deleteUser(@PathVariable Long id) throws NotFoundException {
        return ApiResponse.ok(userService.delete(id));
    }

}
