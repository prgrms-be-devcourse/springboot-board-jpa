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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    /**
     * 유저 사이드 API =================================================================================================
     */
    @PostMapping("/sign-up")
    public ResponseEntity<ApiResponse<SignInResponse>> signUp(@RequestBody @Valid CreateUserRequest requestDto) {
        return new ResponseEntity<>(ApiResponse.success(userService.signUp(requestDto)), HttpStatus.CREATED);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<ApiResponse<SignInResponse>> signIn(@RequestBody @Valid SignInRequest requestDto) {
        return new ResponseEntity<>(ApiResponse.success(userService.signIn(requestDto)), HttpStatus.OK);
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> getMyInfo() {
        UserResponse user = userService.getMyInfo();
        return new ResponseEntity<>(ApiResponse.success(user), HttpStatus.OK);
    }

    /**
     * TODO: 파일 분리 필요
     * 어드민 사이드 API =================================================================================================
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getUser(@PathVariable Long id) {
        UserResponse user = userService.getUser(id);
        return new ResponseEntity<>(ApiResponse.success(user), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> updateUser(@PathVariable Long id,
                                                        @RequestBody @Valid UpdateUserRequest requestDto) {
        userService.updateUser(id, requestDto);
        return new ResponseEntity<>(ApiResponse.success(), HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}") //! ids 받아서 처리하는 경우가 더 많음
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(ApiResponse.success(), HttpStatus.NO_CONTENT);
    }
}
