package com.devcourse.bbs.controller;

import com.devcourse.bbs.controller.bind.ApiResponse;
import com.devcourse.bbs.controller.bind.UserCreateRequest;
import com.devcourse.bbs.controller.bind.UserUpdateRequest;
import com.devcourse.bbs.domain.user.UserDTO;
import com.devcourse.bbs.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{name}")
    public ResponseEntity<ApiResponse<UserDTO>> getUser(@PathVariable(name = "name") String name) {
        UserDTO user = userService.findUserByName(name).orElseThrow(() -> {
            throw new IllegalArgumentException("User with given name not found.");
        });
        return ResponseEntity.ok(ApiResponse.success(user));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UserDTO>> createUser(@Valid @RequestBody UserCreateRequest request) {
        UserDTO user = userService.createUser(request);
        return ResponseEntity
                .created(URI.create(String.format("/users/%s", user.getName())))
                .body(ApiResponse.success(user));
    }

    @PutMapping("/{name}")
    public ResponseEntity<ApiResponse<UserDTO>> updateUser(
            @PathVariable(name = "name") String name,
            @Valid @RequestBody UserUpdateRequest request) {
        return ResponseEntity.ok(ApiResponse.success(userService.updateUser(name, request)));
    }

    @DeleteMapping("/{name}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable(name = "name") String name) {
        userService.deleteUser(name);
    }
}
