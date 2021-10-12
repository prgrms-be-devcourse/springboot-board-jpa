package com.devcourse.springbootboard.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devcourse.springbootboard.user.dto.UserDeleteResponse;
import com.devcourse.springbootboard.user.dto.UserResponse;
import com.devcourse.springbootboard.user.dto.UserSignUpRequest;
import com.devcourse.springbootboard.user.dto.UserUpdateRequest;
import com.devcourse.springbootboard.user.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping
	public ResponseEntity<UserResponse> createUser(@RequestBody final UserSignUpRequest userSignUpRequest) {
		return ResponseEntity.ok(userService.saveUser(userSignUpRequest));
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserResponse> getUser(@PathVariable final Long id) {
		return ResponseEntity.ok(userService.findUser(id));
	}

	@PutMapping("/{id}/profile")
	public ResponseEntity<UserResponse> modifyUser(
		@PathVariable final Long id,
		@RequestBody final UserUpdateRequest userUpdateRequest
	) {
		return ResponseEntity.ok(userService.updateUser(id, userUpdateRequest));
	}

	@DeleteMapping("{id}")
	public ResponseEntity<UserDeleteResponse> removeUser(@PathVariable final Long id) {
		return ResponseEntity.ok(userService.deleteUser(id));
	}
}
