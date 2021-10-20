package com.example.boardbackend.controller;

import com.example.boardbackend.common.converter.ResponseConverter;
import com.example.boardbackend.dto.request.LoginRequest;
import com.example.boardbackend.dto.UserDto;
import com.example.boardbackend.dto.response.UserIdResponse;
import com.example.boardbackend.common.error.exception.IllegalArgException;
import com.example.boardbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/api/user")
@RestController
public class UserController {
    private final UserService userService;
    private final ResponseConverter responseConverter;

    // 회원가입
    @PostMapping
    public ResponseEntity<UserDto> signUp(@Valid @RequestBody UserDto userDto){
        UserDto response = userService.saveUser(userDto);
        return ResponseEntity.ok(response);
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<UserIdResponse> login(@Valid @RequestBody LoginRequest loginRequest){
        UserIdResponse response = userService.login(loginRequest);
        return ResponseEntity.ok(response);
    }

    // id로 회원정보 조회
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserInfo(@PathVariable("id") Long id){
        UserDto response = userService.findUserById(id);
        return ResponseEntity.ok(response);
    }

    // 회원탈퇴
    @DeleteMapping("/{id}")
    public ResponseEntity resign(@PathVariable("id") Long id){
        userService.deleteUserById(id);
        return ResponseEntity.ok().build();
    }

}
