package com.prgrms.boardjpa.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.boardjpa.post.NotExistException;
import com.prgrms.boardjpa.user.dto.UserDto;

@Service
@Transactional(readOnly = true)
public class UserService {
	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Transactional
	public UserDto.Info create(UserDto.CreateRequest request) {
		User newUser = userRepository.save(
			request.createUser()
		);

		return UserDto.Info.from(newUser);
	}

	public User getById(Long writerId) {
		return userRepository.findById(writerId)
			.orElseThrow(NotExistException::new);
	}
}
