package com.example.board.controller;

import com.example.board.dto.request.user.UpdateUserRequest;
import com.example.board.dto.response.ApiResponse;
import com.example.board.dto.response.UserResponse;
import com.example.board.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getUser(@PathVariable Long id) {
        UserResponse user = userService.getUser(id);
        return ResponseEntity.ok(ApiResponse.success(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> updateUser(@PathVariable Long id,
                                                        @RequestBody @Valid UpdateUserRequest requestDto) {
        userService.updateUser(id, requestDto);
        return ResponseEntity.ok(ApiResponse.success());

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(ApiResponse.success());
    }
}
