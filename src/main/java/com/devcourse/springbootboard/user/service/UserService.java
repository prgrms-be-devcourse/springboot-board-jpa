package com.devcourse.springbootboard.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devcourse.springbootboard.user.converter.UserConverter;
import com.devcourse.springbootboard.user.domain.User;
import com.devcourse.springbootboard.user.dto.UserResponse;
import com.devcourse.springbootboard.user.dto.UserSignUpRequest;
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
}
