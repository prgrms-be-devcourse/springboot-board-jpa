package com.kdt.simpleboard.user.controller;

import com.kdt.simpleboard.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.kdt.simpleboard.user.dto.UserRequest.SignUpReq;
import static com.kdt.simpleboard.user.dto.UserResponse.SignUpRes;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<SignUpRes> createUser(@Valid @RequestBody SignUpReq request){
        SignUpRes response = userService.createUser(request);
        return ResponseEntity.ok(response);
    }
}
