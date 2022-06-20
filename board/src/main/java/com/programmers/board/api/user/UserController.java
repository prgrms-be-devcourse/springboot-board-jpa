package com.programmers.board.api.user;

import com.programmers.board.core.user.application.UserService;
import com.programmers.board.core.user.application.dto.UserCreateRequest;
import com.programmers.board.core.user.application.dto.UserResponse;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestController
@RestControllerAdvice
@RequestMapping("/api/v1/users")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/join")
  public ResponseEntity<UserResponse> join(@RequestBody UserCreateRequest userCreateRequest) {
    UserResponse joinedUser = userService.join(userCreateRequest);
    URI location = URI.create("/api/v1/users/" + joinedUser.getId());
    return ResponseEntity.created(location).body(joinedUser);
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {
    return ResponseEntity.ok(userService.getUser(id));
  }
}
