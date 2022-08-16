package com.prgrms.boardjpa.application.user.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prgrms.boardjpa.application.user.UserDto;
import com.prgrms.boardjpa.application.user.service.UserService;
import com.prgrms.boardjpa.core.commons.api.SuccessResponse;

@RequestMapping("/api/users")
@RestController
public class UserRestController {
	private final UserService userService;

	public UserRestController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping
	public ResponseEntity<SuccessResponse<UserDto.Info>> create(@RequestBody @Valid UserDto.CreateRequest createRequest) {
		return ResponseEntity.ok(
			SuccessResponse.of(
				userService.store(createRequest)
			)
		);
	}

}
