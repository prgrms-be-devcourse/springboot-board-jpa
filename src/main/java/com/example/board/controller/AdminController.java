package com.example.board.controller;

import com.example.board.dto.request.admin.AdminUpdateUserRequest;
import com.example.board.dto.response.ApiResponse;
import com.example.board.dto.response.PostResponse;
import com.example.board.dto.response.UserDetailResponse;
import com.example.board.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final AdminService adminService;
    
    @GetMapping("/users")
    public ResponseEntity<ApiResponse<List<UserDetailResponse>>> getUsers(@RequestParam(required = false) List<Long> userIds) {
        return ResponseEntity.ok(ApiResponse.success(adminService.getUsers(userIds)));
    }

    @PatchMapping("/users/{id}")
    public ResponseEntity<ApiResponse<List<UserDetailResponse>>> updateUser(@PathVariable Long id, @RequestBody @Valid AdminUpdateUserRequest requestDto) {
        return ResponseEntity.ok(ApiResponse.success(adminService.updateUser(id, requestDto)));
    }

    @DeleteMapping("/users")
    public ResponseEntity<ApiResponse<List<UserDetailResponse>>> deleteUsers(@RequestParam List<Long> userIds) {
        return ResponseEntity.ok(ApiResponse.success(adminService.deleteUsers(userIds)));
    }

    @GetMapping("/posts/users/{userId})")
    public ResponseEntity<ApiResponse<List<PostResponse>>> getPostsFromUser(@PathVariable Long userId) {
        return ResponseEntity.ok(ApiResponse.success(adminService.getPosts(userId)));
    }

    @DeleteMapping("/posts")
    public ResponseEntity<ApiResponse<List<UserDetailResponse>>> getUsersPosts(@RequestParam List<Long> postIds) {
        return ResponseEntity.ok(ApiResponse.success(adminService.deletePosts(postIds)));
    }
}
