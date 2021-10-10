package org.prgms.board.user.controller;

import org.prgms.board.common.ResponseHandler;
import org.prgms.board.user.dto.UserRequest;
import org.prgms.board.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Object> getOne(@PathVariable Long userId) {
        return ResponseHandler.generateResponse("Successfully retrieved data!", HttpStatus.OK, userService.getOneUser(userId));
    }

    @PostMapping
    public ResponseEntity<Object> addUser(@RequestBody UserRequest userRequest) {
        return ResponseHandler.generateResponse("Successfully added data!", HttpStatus.OK, userService.addUser(userRequest));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Object> modifyUser(@PathVariable Long userId, @RequestBody UserRequest userRequest) {
        return ResponseHandler.generateResponse("Successfully updated data!", HttpStatus.OK, userService.modifyUser(userId, userRequest));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> removeUser(@PathVariable Long userId) {
        userService.removeUser(userId);
        return ResponseHandler.generateResponse("Successfully removed data!", HttpStatus.OK, null);
    }

}

