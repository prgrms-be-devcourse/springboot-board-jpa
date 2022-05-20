package com.prgrms.board.user.controller;

import com.prgrms.board.common.ApiResponse;
import com.prgrms.board.user.dto.UserRequest;
import com.prgrms.board.user.dto.UserResponse;
import com.prgrms.board.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @PostMapping
    public ApiResponse<UserResponse> save(
            @RequestBody UserRequest userRequest
    ) {
        UserResponse userResponse = userService.insert(userRequest);
        return ApiResponse.ok(userResponse);
    }

    @GetMapping("/{userId}")
    public ApiResponse<UserResponse> getOne(
            @PathVariable Long userId
    ) {
        UserResponse user = userService.findOne(userId);
        return ApiResponse.ok(user);
    }

    @GetMapping
    public ApiResponse<Slice<UserResponse>> getAll(
            @RequestParam String hobby,
            Pageable pageable
    ) {
        Slice<UserResponse> userList = userService.findAllByHobby(hobby, pageable);
        return ApiResponse.ok(userList);
    }

    @DeleteMapping("/{userId}")
    public ApiResponse<String> deleteOne(
            @PathVariable Long userId
    ) {
        userService.deleteById(userId);
        return ApiResponse.ok();
    }

}
