package com.devcourse.springbootboard.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devcourse.springbootboard.global.error.exception.ErrorCode;
import com.devcourse.springbootboard.user.converter.UserConverter;
import com.devcourse.springbootboard.user.domain.Hobby;
import com.devcourse.springbootboard.user.domain.User;
import com.devcourse.springbootboard.user.dto.UserResponse;
import com.devcourse.springbootboard.user.dto.UserSignUpRequest;
import com.devcourse.springbootboard.user.dto.UserUpdateRequest;
import com.devcourse.springbootboard.user.exception.UserNotFoundException;
import com.devcourse.springbootboard.user.repository.UserRepository;

@Service
public class UserService {
	private UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Transactional
	public UserResponse saveUser(final UserSignUpRequest userSignUpRequest) {
		User user = UserConverter.convertUserSignUpRequest(userSignUpRequest);

		return UserConverter.toUserResponse(userRepository.save(user));
	}

	@Transactional(readOnly = true)
	public UserResponse findUser(final Long id) {
		User user = userRepository.findById(id)
			.orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));

		return UserConverter.toUserResponse(user);
	}

	@Transactional
	public UserResponse updateUser(Long id, UserUpdateRequest userUpdateRequest) {
		User user = userRepository.findById(id)
			.orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));

		user.changeInfo(userUpdateRequest.getName(), userUpdateRequest.getAge(),
			new Hobby(userUpdateRequest.getHobby()));

		return UserConverter.toUserResponse(user);
	}

	@Transactional
	public Long deleteUser(Long id) {
		User user = userRepository.findById(id)
			.orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));

		userRepository.delete(user);

		return id;
	}
}
