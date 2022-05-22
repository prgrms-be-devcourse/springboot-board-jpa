package com.will.jpapractice.domain.user.api;

import com.will.jpapractice.domain.user.dto.UserRequest;
import com.will.jpapractice.domain.user.dto.UserResponse;
import com.will.jpapractice.global.common.response.ApiResponse;
import com.will.jpapractice.domain.user.application.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ApiResponse<Page<UserResponse>> getAll(Pageable pageable) {
        return ApiResponse.ok(userService.findUsers(pageable));
    }

    @PostMapping
    public ApiResponse<Long> save(@Valid @RequestBody UserRequest userRequest) {
        Long id = userService.save(userRequest);
        return ApiResponse.ok(id);
    }
}
