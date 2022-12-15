package com.prgrms.devcourse.springjpaboard.domain.user.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prgrms.devcourse.springjpaboard.domain.user.api.dto.UserRequestDto;
import com.prgrms.devcourse.springjpaboard.domain.user.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

	private final UserService userService;

	@PostMapping
	public ResponseEntity<Void> create(@Valid @RequestBody UserRequestDto userRequestDto) {
		userService.create(userRequestDto);

		return ResponseEntity.ok().build();
	}

}
