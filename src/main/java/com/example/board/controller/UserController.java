package com.example.board.controller;

import com.example.board.domain.service.UserService;
import com.example.board.dto.user.UserResponseDto;
import com.example.board.dto.user.UserSaveRequestDto;
import com.example.board.dto.user.UserUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("users")
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<Long> createUser(@Valid @RequestBody UserSaveRequestDto requestDto) {
        Long userId = userService.createUser(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userId);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDto> findUser(@PathVariable Long userId) {
        UserResponseDto responseDto = userService.findUser(userId);
        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<Void> updateUser(@PathVariable Long userId,
                                           @Valid @RequestBody UserUpdateRequestDto requestDto) {
        userService.updateUser(userId, requestDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
