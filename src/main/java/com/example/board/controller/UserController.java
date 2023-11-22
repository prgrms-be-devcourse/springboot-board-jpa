package com.example.board.controller;

import com.example.board.dto.request.user.CreateUserRequest;
import com.example.board.dto.request.user.UpdateUserRequest;
import com.example.board.dto.response.ApiResponse;
import com.example.board.dto.response.UserResponse;
import com.example.board.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse<Long>> createPost(@RequestBody @Valid CreateUserRequest requestDto,
                                                        UriComponentsBuilder uriComponentsBuilder) {
        Long id = userService.createUser(requestDto);
        URI location = uriComponentsBuilder.path("/v1/users/{id}")
                .buildAndExpand(id)
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);

        ApiResponse<Long> apiResponse = ApiResponse.success(HttpStatus.CREATED, id);
        return new ResponseEntity<>(apiResponse, headers, apiResponse.getStatusCode());
    }

    @GetMapping("/{id}")
    public ApiResponse<UserResponse> getUser(@PathVariable Long id) {
        UserResponse user = userService.getUser(id);
        return ApiResponse.success(HttpStatus.OK, user);
    }

    @PutMapping("/{id}")
    public ApiResponse<Void> updateUser(@PathVariable Long id,
                                        @RequestBody @Valid UpdateUserRequest requestDto) {
        userService.updateUser(id, requestDto);
        return ApiResponse.success(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ApiResponse.success(HttpStatus.NO_CONTENT);
    }
}
