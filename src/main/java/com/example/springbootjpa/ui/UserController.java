package com.example.springbootjpa.ui;


import com.example.springbootjpa.application.UserService;
import com.example.springbootjpa.ui.dto.user.UserFindResponse;
import com.example.springbootjpa.ui.dto.user.UserSaveRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Map<String, Long>> createUser(@RequestBody UserSaveRequest userSaveRequest) {
        long saveId = userService.createUser(
                userSaveRequest.name(),
                userSaveRequest.age(),
                userSaveRequest.hobby());

        return ResponseEntity.created(URI.create("/api/v1/users/" + saveId)).body(Collections.singletonMap("id", saveId));
    }

    @GetMapping
    public ResponseEntity<List<UserFindResponse>> findAll() {

        return ResponseEntity.ok(userService.findAllUsers());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserFindResponse> findById(@PathVariable Long userId) {

        return ResponseEntity.ok(userService.findById(userId));
    }
}
