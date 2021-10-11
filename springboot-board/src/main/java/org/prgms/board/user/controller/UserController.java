package org.prgms.board.user.controller;

import org.prgms.board.common.response.ApiResponse;
import org.prgms.board.user.dto.UserRequest;
import org.prgms.board.user.dto.UserResponse;
import org.prgms.board.user.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public ApiResponse<UserResponse> getOne(@PathVariable Long userId) {
        return ApiResponse.toResponse(userService.getOneUser(userId));
    }

    @PostMapping
    public ApiResponse<Long> addUser(@RequestBody UserRequest userRequest) {
        return ApiResponse.toResponse(userService.addUser(userRequest));
    }

    @PutMapping("/{userId}")
    public ApiResponse<Long> modifyUser(@PathVariable Long userId, @RequestBody UserRequest userRequest) {
        return ApiResponse.toResponse(userService.modifyUser(userId, userRequest));
    }

    @DeleteMapping("/{userId}")
    public ApiResponse<Integer> removeUser(@PathVariable Long userId) {
        userService.removeUser(userId);
        return ApiResponse.toResponse(1);
    }

}

