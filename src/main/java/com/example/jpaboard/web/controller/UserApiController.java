package com.example.jpaboard.web.controller;

import com.example.jpaboard.service.dto.user.UserSaveRequest;
import com.example.jpaboard.service.user.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

@RequestMapping("api/v1/users")
@RestController
public class UserApiController {
    private final UserService userService;

    public UserApiController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public Long save(@RequestBody @Valid UserSaveRequest userSaveRequest) {
        return userService.save(userSaveRequest.getName(), userSaveRequest.getAge(), userSaveRequest.getHobby());
    }
}
