package org.prgms.board.user.controller;

import org.prgms.board.common.response.ApiResponse;
import org.prgms.board.user.dto.UserRequest;
import org.prgms.board.user.dto.UserResponse;
import org.prgms.board.user.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ApiResponse<UserResponse> getOne(@PathVariable Long id) {
        return ApiResponse.toResponse(userService.getUser(id));
    }

    @PostMapping
    public ApiResponse<Long> addUser(@RequestBody @Valid UserRequest userRequest) {
        return ApiResponse.toResponse(userService.addUser(userRequest));
    }

    @PutMapping("/{id}")
    public ApiResponse<Long> modifyUser(@PathVariable Long id, @RequestBody @Valid UserRequest userRequest) {
        return ApiResponse.toResponse(userService.modifyUser(id, userRequest));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Long> removeUser(@PathVariable Long id) {
        userService.removeUser(id);
        return ApiResponse.ok();
    }

}

