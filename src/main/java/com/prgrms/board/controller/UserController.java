package com.prgrms.board.controller;

import com.prgrms.board.dto.user.UserGetRequest;
import com.prgrms.board.dto.user.UserResponse;
import com.prgrms.board.dto.user.UserSaveRequest;
import com.prgrms.board.dto.user.UserUpdateRequest;
import com.prgrms.board.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> createUser(
            @RequestBody @Valid UserSaveRequest saveRequest
    ) {
        UserResponse userResponse = userService.createUser(saveRequest);
        String resourceUrl = "/users/" + userResponse.getUserId();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(resourceUrl));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .headers(headers)
                .body(userResponse);
    }

    @GetMapping
    public ResponseEntity<UserResponse> findUserByEmail(
            @RequestBody @Valid UserGetRequest getRequest
    ) {
        UserResponse userResponse = userService.findByEmail(getRequest.getEmail());
        return ResponseEntity.ok(userResponse);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> findUserById(
            @PathVariable Long userId
    ) {
        UserResponse userResponse = userService.findById(userId);
        return ResponseEntity.ok(userResponse);
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<UserResponse> updateUser(
            @RequestBody @Valid UserUpdateRequest updateRequest,
            @PathVariable Long userId
    ) {
        UserResponse userResponse = userService.updateUser(updateRequest, userId);
        return ResponseEntity.ok(userResponse);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable Long userId
    ) {
        userService.deleteUserById(userId);
        return ResponseEntity.noContent().build();
    }
}
