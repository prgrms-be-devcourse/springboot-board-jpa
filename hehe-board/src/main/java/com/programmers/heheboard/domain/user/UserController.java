package com.programmers.heheboard.domain.user;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.programmers.heheboard.global.response.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;

	@PostMapping
	public ApiResponse<UserResponseDto> createUser(@Valid @RequestBody CreateUserRequestDto createUserRequestDto) {
		return ApiResponse.ok(userService.createUser(createUserRequestDto));
	}
}
