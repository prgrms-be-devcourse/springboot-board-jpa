package org.prgrms.myboard.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.prgrms.myboard.dto.UserCreateRequestDto;
import org.prgrms.myboard.dto.UserResponseDto;
import org.prgrms.myboard.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/users")
@RestController
@RequiredArgsConstructor
public class UserRestController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserCreateRequestDto userCreateRequestDto) {
        return ResponseEntity.ok(userService.createUser(userCreateRequestDto));
    }
}
