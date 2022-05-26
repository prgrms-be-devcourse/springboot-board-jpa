package com.example.springbootboardjpa.user.controller;

import com.example.springbootboardjpa.user.dto.CreateUserRequest;
import com.example.springbootboardjpa.user.dto.UserDto;
import com.example.springbootboardjpa.user.dto.UserResponse;
import com.example.springbootboardjpa.user.service.UserService;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> notFoundHandler() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> internalExceptionHandler(Exception e) {
        return ResponseEntity.internalServerError().body(e.getMessage());
    }

    @PutMapping
    public ResponseEntity<UserResponse> insert(@RequestBody CreateUserRequest createUserRequest) {
        UserResponse userResponse = userService.insert(createUserRequest);
        return ResponseEntity.created(URI.create("/api/v1/users/" + userResponse.getId().toString())).body(userResponse);
    }

    @GetMapping
    public ResponseEntity<Page<UserResponse>> getAll(Pageable pageable) {
        return ResponseEntity.ok(userService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getById(@PathVariable UUID id) throws NotFoundException {
        UserResponse byId = userService.findById(id);
        return ResponseEntity.ok(byId);
    }

    @PatchMapping
    public ResponseEntity<UserResponse> modify(@RequestBody UserDto userDto) {
        UserResponse update = userService.update(userDto);
        return ResponseEntity.ok(update);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> remove(@PathVariable UUID id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
