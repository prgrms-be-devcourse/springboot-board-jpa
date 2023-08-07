package com.prgrms.boardjpa.User.presentation;

import com.prgrms.boardjpa.User.application.UserService;
import com.prgrms.boardjpa.User.dto.UserRequest;
import com.prgrms.boardjpa.User.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserApiController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> join(@Validated @RequestBody UserRequest request) {
        return ResponseEntity.ok(userService.join(request));
    }
}