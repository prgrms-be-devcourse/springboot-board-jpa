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
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    /**
     * 유저 사이드 API =================================================================================================
     */
    @PostMapping("/sign-up")
    public ResponseEntity<ApiResponse<Long>> createUser(@RequestBody @Valid CreateUserRequest requestDto) {
        Long id = userService.signUp(requestDto);
        URI location = UriComponentsBuilder
                .fromPath("/v1/users/{id}") //? 유저 사이드라면 어떤 식으로 path 전달해야 하는지? /v1/users/{id}는 어드민 사이드 아닌가?
                .buildAndExpand(id)
                .toUri();
        return ResponseEntity.created(location).body(ApiResponse.success(HttpStatus.CREATED, id));
    }

    @PostMapping("/sign-in")
    public ResponseEntity<ApiResponse<SignInResponse>> signIn(@RequestBody @Valid SignInRequest requestDto) {
        return ResponseEntity.ok().body(ApiResponse.success(HttpStatus.OK, userService.signIn(requestDto)));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> getMyInfo() {
        UserResponse user = userService.getMyInfo();
        return ResponseEntity.ok().body(ApiResponse.success(HttpStatus.OK, user));
    }

    /**
     * TODO: 파일 분리 필요
     * 어드민 사이드 API =================================================================================================
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getUser(@PathVariable Long id) {
        UserResponse user = userService.getUser(id);
        return ResponseEntity.ok().body(ApiResponse.success(HttpStatus.OK, user));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> updateUser(@PathVariable Long id,
                                                        @RequestBody @Valid UpdateUserRequest requestDto) {
        userService.updateUser(id, requestDto);
        return ResponseEntity.ok().body(ApiResponse.success(HttpStatus.OK)); //TODO: 204?
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}") //! ids 받아서 처리하는 경우가 더 많음
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().body(ApiResponse.success(HttpStatus.OK)); //TODO: 204?
    }
}
