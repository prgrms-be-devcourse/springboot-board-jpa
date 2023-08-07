package com.example.springbootjpa.ui;


import com.example.springbootjpa.application.UserService;
import com.example.springbootjpa.domain.user.Hobby;
import com.example.springbootjpa.domain.user.User;
import com.example.springbootjpa.ui.dto.user.UserFindResponses;
import com.example.springbootjpa.domain.user.UserRepository;
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
    private final UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
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
    public ResponseEntity<UserFindResponses> findAll() {
        UserFindResponses userFindResponses = new UserFindResponses(userService.findAllUsers());

        return ResponseEntity.ok(userFindResponses);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserFindResponse> findById(@PathVariable Long userId) {

        return ResponseEntity.ok(userService.find(userId));
    }
}
