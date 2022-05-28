package org.prgrms.board.service.user;

import static com.google.common.base.Preconditions.*;

import java.util.List;

import org.prgrms.board.domain.user.User;
import org.prgrms.board.domain.user.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public List<User> findAll() {
		return userRepository.findAll();
	}

	public User findById(Long id) {
		checkArgument(id != null, "userId must be provided.");

		return userRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("Could not found user with userId=" + id));
	}
}