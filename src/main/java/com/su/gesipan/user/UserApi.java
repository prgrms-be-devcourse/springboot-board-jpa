package com.su.gesipan.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserApi {
    private final UserService userService;

    // 유저 생성
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    UserDto.Result createUser(@Validated @RequestBody UserDto.Create dto) {
        return userService.createUser(dto);
    }
}
