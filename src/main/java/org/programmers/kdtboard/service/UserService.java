package org.programmers.kdtboard.service;

import static java.text.MessageFormat.*;
import static org.programmers.kdtboard.converter.Converter.*;

import org.programmers.kdtboard.controller.response.ErrorCodeMessage;
import org.programmers.kdtboard.converter.Converter;
import org.programmers.kdtboard.domain.user.User;
import org.programmers.kdtboard.domain.user.UserRepository;
import org.programmers.kdtboard.dto.UserDto.UserResponseDto;
import org.programmers.kdtboard.exception.NotFoundUserException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class UserService {

	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public UserResponseDto create(String name, int age, String hobby) {
		var user = User.create(name, age, hobby);
		var save = userRepository.save(user);

		return userConverter(save);
	}

	public UserResponseDto findById(Long id) {
		var foundUser = userRepository.findById(id)
			.orElseThrow(
				() -> new NotFoundUserException(format("{0}는 없는 ID 입니다.", id),ErrorCodeMessage.USER_ID_NOT_FOUND));

		return userConverter(foundUser);
	}

	public Page<UserResponseDto> findAll(Pageable pageable) {
		return this.userRepository.findAll(pageable).map(Converter::userConverter);
	}
}
