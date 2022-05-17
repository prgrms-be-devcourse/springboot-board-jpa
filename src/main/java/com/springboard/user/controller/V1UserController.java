package com.springboard.user.controller;

import com.springboard.user.dto.CreateUserRequest;
import com.springboard.user.dto.CreateUserResponse;
import com.springboard.user.dto.FindUserResponse;
import com.springboard.user.dto.UpdateUserRequest;
import com.springboard.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class V1UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<Page<FindUserResponse>> getAll(Pageable pageable) {
        Page<FindUserResponse> response = userService.findAll(pageable);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CreateUserResponse> register(@RequestBody CreateUserRequest request) {
        CreateUserResponse response = userService.save(request);
        return ResponseEntity.created(URI.create("/api/v1/users/" + response.id())).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FindUserResponse> getOne(@PathVariable Long id) {
        FindUserResponse response = userService.findOne(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FindUserResponse> modify(@PathVariable Long id, @RequestBody UpdateUserRequest request) {
        FindUserResponse response = userService.updateOne(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> remove(@PathVariable Long id) {
        userService.deleteOne(id);
        return ResponseEntity.noContent().build();
    }
}
