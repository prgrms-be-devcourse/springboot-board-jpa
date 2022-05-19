package org.programmers.springbootboardjpa.controller;

import lombok.RequiredArgsConstructor;
import org.programmers.springbootboardjpa.controller.dto.IdResponse;
import org.programmers.springbootboardjpa.controller.dto.UserCreateForm;
import org.programmers.springbootboardjpa.controller.dto.UserDto;
import org.programmers.springbootboardjpa.domain.User;
import org.programmers.springbootboardjpa.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @PostMapping("/api/v1/users")
    public ResponseEntity<IdResponse> postUser(@RequestBody UserCreateForm userCreateForm) {
        Long id = userService.registerUser(userCreateForm);
        //TODO PR 포인트: SingletonMap?
        //TODO PR 포인트: 사용자 ID 값은 데이터베이스에서 사용자를 식별할 수 있는 값인데, 노출하면 안 되지 않나? (개인정보보호법) 일단 카카오는 회원 ID를 넘겨줌
        return ResponseEntity.created(URI.create("/api/v1/users/" + id)).body(new IdResponse(id));
    }

    @GetMapping("/api/v1/users/{id}")
    public UserDto users(@PathVariable("id") Long userId) {
        User user = userService.findUserWith(userId);

        return null;
    }
}