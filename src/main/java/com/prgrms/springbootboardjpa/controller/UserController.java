package com.prgrms.springbootboardjpa.controller;

import com.prgrms.springbootboardjpa.dto.UserRequest;
import com.prgrms.springbootboardjpa.dto.UserResponse;
import com.prgrms.springbootboardjpa.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/api/v1/")
@RestController
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("users/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getOne(@PathVariable long id) {
        return ResponseEntity.ok(ApiResponse.of(userService.getOne(id)));
    }

    @GetMapping("users")
    public ResponseEntity<ApiResponse<Page<UserResponse>>> getAll(Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.of(userService.getAll(pageable)));
    }

    @PostMapping("users")
    public ResponseEntity<ApiResponse<Long>> save(@RequestBody @Valid UserRequest userRequest) {
        return ResponseEntity.ok(ApiResponse.of(userService.save(userRequest)));
    }

    @PostMapping("users/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> update(@PathVariable long id, @RequestBody @Valid UserRequest userRequest) {
        return ResponseEntity.ok(ApiResponse.of(userService.update(id, userRequest)));
    }

    @DeleteMapping("users/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable long id) {
        userService.delete(id);
        return ResponseEntity.ok(ApiResponse.of("성공적으로 삭제 됐습니다."));
    }
}
