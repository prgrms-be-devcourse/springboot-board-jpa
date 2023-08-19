package com.programmers.jpa_board.user.ui;

import com.programmers.jpa_board.global.ApiResponse;
import com.programmers.jpa_board.user.application.UserServiceImpl;
import com.programmers.jpa_board.user.domain.dto.UserDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping
    public ApiResponse<UserDto.UserResponse> save(@RequestBody @Valid UserDto.CreateUserRequest request) {
        UserDto.UserResponse response = userService.save(request);

        return ApiResponse.created(response);
    }
}
