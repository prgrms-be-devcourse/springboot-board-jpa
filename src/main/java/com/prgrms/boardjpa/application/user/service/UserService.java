package com.prgrms.boardjpa.application.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.boardjpa.application.user.UserConverter;
import com.prgrms.boardjpa.application.user.UserDto;
import com.prgrms.boardjpa.application.user.model.User;
import com.prgrms.boardjpa.application.user.repository.UserRepository;
import com.prgrms.boardjpa.core.commons.exception.NotExistException;

@Service
@Transactional(readOnly = true)
public class UserService {
	private final UserRepository userRepository;
	private final UserConverter userConverter;

	public UserService(UserRepository userRepository, UserConverter userConverter) {
		this.userRepository = userRepository;
		this.userConverter = userConverter;
	}

	@Transactional
	public UserDto.Info store(UserDto.CreateRequest request) {
		User newUser = userRepository.save(
			userConverter.toEntity(request)
		);
		return userConverter.from(newUser);
	}

	public UserDto.Info getById(Long writerId) {
		return userRepository.findById(writerId)
			.map(userConverter::from)
			.orElseThrow(NotExistException::new);
	}
}
