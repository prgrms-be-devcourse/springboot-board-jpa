package com.prgrms.board.controller;

import com.prgrms.board.dto.IdResponse;
import com.prgrms.board.dto.user.UserCreateRequest;
import com.prgrms.board.dto.user.UserFindRequest;
import com.prgrms.board.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@Validated
@RequestMapping("/users")
@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<IdResponse> createUser(final @Valid @RequestBody UserCreateRequest userRequest) {
        return ResponseEntity.ok(userService.createUser(userRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserFindRequest> findUser(final @PathVariable @Positive Long id) {
        return ResponseEntity.ok(userService.findUser(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<IdResponse> modifyUser(final @PathVariable Long id, final @Valid @RequestBody UserCreateRequest userCreateRequest) {
        return ResponseEntity.ok(userService.modifyUser(id, userCreateRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<IdResponse> removeUser(final @PathVariable @Positive Long id) {
        return ResponseEntity.ok(userService.removeUser(id));
    }

}
