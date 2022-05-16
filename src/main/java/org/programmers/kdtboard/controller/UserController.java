package org.programmers.kdtboard.controller;

import javax.validation.Valid;

import org.programmers.kdtboard.controller.response.ApiResponse;
import org.programmers.kdtboard.dto.UserDto.UserCreateDto;
import org.programmers.kdtboard.dto.UserDto.UserResponseDto;
import org.programmers.kdtboard.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/api/v1/user")
	public ApiResponse<UserResponseDto> create(@RequestBody @Valid UserCreateDto userCreateDto) {
		var userResponseDto = this.userService.create(userCreateDto.name(), userCreateDto.age(), userCreateDto.hobby());

		return ApiResponse.ok(userResponseDto);
	}

	@GetMapping("/api/v1/users")
	public ApiResponse<Page<UserResponseDto>> findAll(Pageable pageable) {
		return ApiResponse.ok(this.userService.findAll(pageable));
	}

}
