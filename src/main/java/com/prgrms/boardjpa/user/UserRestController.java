package com.prgrms.boardjpa.user;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prgrms.boardjpa.commons.api.SuccessResponse;

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
