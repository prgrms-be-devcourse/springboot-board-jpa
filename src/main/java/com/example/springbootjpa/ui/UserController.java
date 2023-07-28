package com.example.springbootjpa.ui;


import com.example.springbootjpa.application.UserService;
import com.example.springbootjpa.ui.dto.user.UserDto;
import com.example.springbootjpa.ui.dto.user.UserSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Long>> createUser(@RequestBody UserSaveRequestDto userSaveRequestDto) {
        long saveId = userService.createUser(
                userSaveRequestDto.name(),
                userSaveRequestDto.age(),
                userSaveRequestDto.hobby());

        return ResponseEntity.created(URI.create("/api/v1/users/" + saveId)).body(Collections.singletonMap("id", saveId));
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> findAll() {

        return ResponseEntity.ok(userService.findAllUsers());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> findById(@PathVariable Long userId) {

        return ResponseEntity.ok(userService.findById(userId));
    }
}
