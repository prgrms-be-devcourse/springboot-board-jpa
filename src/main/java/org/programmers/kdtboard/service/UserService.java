package org.programmers.kdtboard.service;

import static java.text.MessageFormat.*;

import org.programmers.kdtboard.controller.response.ErrorCodeMessage;
import org.programmers.kdtboard.converter.Converter;
import org.programmers.kdtboard.domain.user.User;
import org.programmers.kdtboard.domain.user.UserRepository;
import org.programmers.kdtboard.dto.UserDto.UserResponseDto;
import org.programmers.kdtboard.exception.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class UserService {

	private final UserRepository userRepository;
	private final Converter converter;

	public UserService(UserRepository userRepository, Converter converter) {
		this.userRepository = userRepository;
		this.converter = converter;
	}

	public UserResponseDto create(String name, int age, String hobby) {
		var user = User.create(name, age, hobby);
		var save = userRepository.save(user);

		return this.converter.userConverter(save);
	}

	public UserResponseDto findById(Long id) {
		var foundUser = userRepository.findById(id)
			.orElseThrow(
				() -> new EntityNotFoundException(format("user id : {0}, 없는 ID 입니다.", id),
					ErrorCodeMessage.USER_ID_NOT_FOUND));

		return this.converter.userConverter(foundUser);
	}

	public Page<UserResponseDto> findAll(Pageable pageable) {
		return this.userRepository.findAll(pageable).map(this.converter::userConverter);
	}
}
