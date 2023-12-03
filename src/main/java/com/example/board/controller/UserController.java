package com.example.board.controller;

import com.example.board.annotation.AuthUser;
import com.example.board.dto.request.user.SelfUpdateUserRequest;
import com.example.board.dto.response.ApiResponse;
import com.example.board.dto.response.UserSummaryResponse;
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

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserSummaryResponse>> getMyInfo(@AuthUser Long id) {
        UserSummaryResponse response = userService.getUser(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PatchMapping("/me")
    public ResponseEntity<ApiResponse<Void>> updateUser(@AuthUser Long id, @RequestBody @Valid SelfUpdateUserRequest requestDto) {
        userService.updateUser(id, requestDto);
        return ResponseEntity.ok(ApiResponse.success());
    }

    @DeleteMapping("/me")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@AuthUser Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(ApiResponse.success());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserSummaryResponse>> getUser(@PathVariable Long id) {
        UserSummaryResponse user = userService.getUser(id);
        return ResponseEntity.ok(ApiResponse.success(user));
    }
}
