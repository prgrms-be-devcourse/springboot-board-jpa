package com.example.boardbackend.controller;

import com.example.boardbackend.dto.UserDto;
import com.example.boardbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RequestMapping("/api/user")
@RestController
public class UserController {
    private final UserService userService;

    // 회원가입
    @PostMapping
    public ResponseEntity<UserDto> signUp(@RequestBody UserDto userDto){
        userDto.setCreatedAt(LocalDateTime.now());
        return ResponseEntity.ok(userService.saveUser(userDto));
    }
}
