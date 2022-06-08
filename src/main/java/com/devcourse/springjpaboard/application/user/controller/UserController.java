package com.devcourse.springjpaboard.application.user.controller;

import static org.springframework.http.HttpStatus.OK;

import com.devcourse.springjpaboard.application.user.controller.dto.CreateUserRequest;
import com.devcourse.springjpaboard.application.user.controller.dto.UserResponse;
import com.devcourse.springjpaboard.application.user.service.UserService;
import com.devcourse.springjpaboard.core.util.ApiResponse;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("")
  public ApiResponse<UserResponse> createUser(@Valid @RequestBody CreateUserRequest createUserRequest) {
    UserResponse user = userService.save(createUserRequest);
    return ApiResponse.ok(OK, user);
  }

  @GetMapping("/{id}")
  public ApiResponse<UserResponse> getUserById(@PathVariable Long id) {
    UserResponse user = userService.findById(id);
    return ApiResponse.ok(OK, user);
  }

}
