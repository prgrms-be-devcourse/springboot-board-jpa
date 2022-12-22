package com.programmers.jpaboard.web.user;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.programmers.jpaboard.domain.user.dto.UserCreateRequestDto;
import com.programmers.jpaboard.domain.user.dto.UserResponseDto;
import com.programmers.jpaboard.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserApiController {

	private final UserService userService;

	@PostMapping
	ResponseEntity<UserResponseDto> createUser(@RequestBody @Valid UserCreateRequestDto userCreateRequestDto) {
		UserResponseDto createdUserDto = userService.createUser(userCreateRequestDto);
		return new ResponseEntity<>(createdUserDto, HttpStatus.CREATED);
	}
}
