package com.example.boardbackend.controller;

import com.example.boardbackend.dto.converter.ResponseConverter;
import com.example.boardbackend.dto.request.LoginRequest;
import com.example.boardbackend.dto.UserDto;
import com.example.boardbackend.dto.response.UserIdResponse;
import com.example.boardbackend.service.UserService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequiredArgsConstructor
@RequestMapping("/api/user")
@RestController
public class UserController {
    private final UserService userService;
    private final ResponseConverter responseConverter;

    // 회원가입
    @PostMapping
    public ResponseEntity<UserDto> signUp(@RequestBody UserDto userDto){
        System.out.println(userDto.toString());
        return ResponseEntity.ok(userService.saveUser(userDto));
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<UserIdResponse> login(@RequestBody LoginRequest loginDto){
        String email = loginDto.getEmail();
        String password = loginDto.getPassword();
        Optional<UserDto> userByEmail = userService.findUserByEmail(email);

        // 가입 X -> 400
        if(userByEmail.isEmpty()){
            return ResponseEntity.badRequest().build();
        }

        // 비밀번호 검증
        String findPW = userByEmail.get().getPassword();
        // 비번 틀림 -> 401
        if(!password.equals(findPW)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        // 검증됨 -> 200 + id 값
        Long findId = userByEmail.get().getId();
        return ResponseEntity.ok(responseConverter.convertToUserId(findId));
    }

    // id로 회원정보 조회
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserInfo(@PathVariable("id") Long id) throws NotFoundException {
        UserDto userById = userService.findUserById(id);
        return ResponseEntity.ok(userById);
    }

    // 회원탈퇴
    @DeleteMapping("/{id}")
    public ResponseEntity resign(@PathVariable("id") Long id){
        userService.deleteUserById(id);
        return ResponseEntity.ok().build();
    }

}
