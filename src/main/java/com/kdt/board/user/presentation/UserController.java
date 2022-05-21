package com.kdt.board.user.presentation;

import com.kdt.board.user.application.UserService;
import com.kdt.board.user.presentation.dto.request.UserRegistrationRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody @Valid final UserRegistrationRequest userRegistrationRequest) {
        userService.register(userRegistrationRequest.toRequestDto());
    }
}
