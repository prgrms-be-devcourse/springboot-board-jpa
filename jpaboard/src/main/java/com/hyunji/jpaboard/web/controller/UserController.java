package com.hyunji.jpaboard.web.controller;

import com.hyunji.jpaboard.domain.user.service.UserService;
import com.hyunji.jpaboard.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/user")
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<String> register(@RequestBody @Validated UserDto userDto) {
        userService.register(userDto.toEntity());
        return ResponseEntity.ok("user registered");
    }
}
