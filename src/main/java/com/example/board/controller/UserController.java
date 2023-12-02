package com.example.board.controller;

import com.example.board.dto.request.user.CreateUserRequest;
import com.example.board.dto.request.user.SignInRequest;
import com.example.board.dto.request.user.SignInResponse;
import com.example.board.dto.request.user.UpdateUserRequest;
import com.example.board.dto.response.ApiResponse;
import com.example.board.dto.response.UserResponse;
import com.example.board.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/sign-in")
    public ResponseEntity<ApiResponse<SignInResponse>> signIn(@RequestBody @Valid SignInRequest requestDto) {
        return ResponseEntity.ok().body(ApiResponse.success(HttpStatus.OK, userService.signIn(requestDto)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Long>> createPost(@RequestBody @Valid CreateUserRequest requestDto) {
        Long id = userService.createUser(requestDto);
        URI location = UriComponentsBuilder
                .fromPath("/v1/users/{id}")
                .buildAndExpand(id)
                .toUri();
        return ResponseEntity.created(location).body(ApiResponse.success(HttpStatus.CREATED, id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getUser(@PathVariable Long id) {
        UserResponse user = userService.getUser(id);
        return ResponseEntity.ok().body(ApiResponse.success(HttpStatus.OK, user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> updateUser(@PathVariable Long id,
                                                        @RequestBody @Valid UpdateUserRequest requestDto) {
        userService.updateUser(id, requestDto);
        return ResponseEntity.ok().body(ApiResponse.success(HttpStatus.OK));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().body(ApiResponse.success(HttpStatus.OK));
    }
}
