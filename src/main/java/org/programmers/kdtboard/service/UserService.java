package org.programmers.kdtboard.service;

import static java.text.MessageFormat.*;

import org.programmers.kdtboard.controller.response.ErrorCode;
import org.programmers.kdtboard.converter.UserConverter;
import org.programmers.kdtboard.domain.user.User;
import org.programmers.kdtboard.domain.user.UserRepository;
import org.programmers.kdtboard.dto.UserDto.Response;
import org.programmers.kdtboard.exception.NotFoundEntityByIdException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class UserService {

	private final UserRepository userRepository;
	private final UserConverter userConverter;

	public UserService(UserRepository userRepository, UserConverter userConverter) {
		this.userRepository = userRepository;
		this.userConverter = userConverter;
	}

	@Transactional
	public Response create(String name, int age, String hobby) {
		var user = User.builder()
			.name(name)
			.age(age)
			.hobby(hobby)
			.build();
		var save = userRepository.save(user);

		return this.userConverter.convertUserResponse(save);
	}

	public Response findById(Long id) {
		var foundUser = userRepository.findById(id)
			.orElseThrow(
				() -> new NotFoundEntityByIdException(format("user id : {0}, 없는 ID 입니다.", id),
					ErrorCode.USER_ID_NOT_FOUND));

		return this.userConverter.convertUserResponse(foundUser);
	}

	public User findEntityById(Long id) {
		return userRepository.findById(id)
			.orElseThrow(
				() -> new NotFoundEntityByIdException(format("user id : {0}, 없는 ID 입니다.", id),
					ErrorCode.USER_ID_NOT_FOUND));
	}

	public Page<Response> findAll(Pageable pageable) {
		return this.userRepository.findAll(pageable).map(this.userConverter::convertUserResponse);
	}
}
