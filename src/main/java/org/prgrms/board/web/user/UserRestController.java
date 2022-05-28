package org.prgrms.board.web.user;

import static org.prgrms.board.web.ApiResponse.*;

import java.util.List;

import org.prgrms.board.domain.user.User;
import org.prgrms.board.service.user.UserService;
import org.prgrms.board.web.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/users")
@RestController
public class UserRestController {

	private final UserService userService;

	public UserRestController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	public ApiResponse<List<UserDto>> findAll() {
		List<UserDto> users = userService.findAll().stream()
			.map(UserDto::new).toList();

		return ok(users);
	}

	@GetMapping("/{id}")
	public ApiResponse<UserDto> findById(@PathVariable Long id) {
		User user = userService.findById(id);

		return ok(new UserDto(user));
	}
}
