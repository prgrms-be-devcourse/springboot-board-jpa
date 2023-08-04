package com.example.springbootjpa.ui;


import com.example.springbootjpa.application.UserService;
import com.example.springbootjpa.ui.dto.user.UserFindResponse;
import com.example.springbootjpa.ui.dto.user.UserSaveRequest;
import com.example.springbootjpa.ui.dto.user.UserSaveResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserSaveResponse> createUser(@RequestBody UserSaveRequest userSaveRequest) {
        long userId = userService.createUser(
                userSaveRequest.name(),
                userSaveRequest.age(),
                userSaveRequest.hobby());

        return ResponseEntity.created(URI.create("/api/v1/users/" + userId)).body(new UserSaveResponse(userId));
    }

    @GetMapping
    public ResponseEntity<List<UserFindResponse>> findAll() {

        return ResponseEntity.ok(userService.findAllUsers());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserFindResponse> findById(@PathVariable Long userId) {

        return ResponseEntity.ok(userService.find(userId));
    }
}
