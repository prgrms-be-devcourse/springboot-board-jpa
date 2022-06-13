package com.programmers.board.api.user;

import com.programmers.board.core.user.application.UserService;
import com.programmers.board.core.user.application.dto.UserCreateRequest;
import com.programmers.board.core.user.application.dto.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RestControllerAdvice
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/join")
    public ResponseEntity<UserResponse> join(@RequestBody UserCreateRequest userCreateRequest){
        UserResponse joinedUser = userService.join(userCreateRequest);
        URI location = URI.create("/api/v1/users/" + joinedUser.getId());
        return ResponseEntity.created(location).body(joinedUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id){
        return ResponseEntity.ok(userService.getUser(id));
    }
}
