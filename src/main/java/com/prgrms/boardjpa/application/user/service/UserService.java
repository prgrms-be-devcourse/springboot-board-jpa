package com.prgrms.boardjpa.application.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.boardjpa.application.user.UserDto;
import com.prgrms.boardjpa.application.user.model.User;
import com.prgrms.boardjpa.application.user.repository.UserRepository;
import com.prgrms.boardjpa.core.commons.exception.NotExistException;

@Service
@Transactional(readOnly = true)
public class UserService {
	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Transactional
	public UserDto.Info store(UserDto.CreateRequest request) {
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
