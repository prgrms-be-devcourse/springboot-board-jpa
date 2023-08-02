package com.programmers.jpa_board.user.ui;

import com.programmers.jpa_board.global.ApiResponse;
import com.programmers.jpa_board.user.application.UserServiceImpl;
import com.programmers.jpa_board.user.domain.dto.request.CreateUserRequest;
import com.programmers.jpa_board.user.domain.dto.response.UserResponse;
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
    public ApiResponse<UserResponse> save(@RequestBody CreateUserRequest request) {
        UserResponse response = userService.create(request);

        return ApiResponse.created(response);
    }
}
