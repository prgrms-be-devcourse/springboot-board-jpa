package com.programmers.jpaboard.domain.user.service;

import org.springframework.stereotype.Service;

import com.programmers.jpaboard.common.util.UserConverter;
import com.programmers.jpaboard.domain.user.dto.UserCreateRequestDto;
import com.programmers.jpaboard.domain.user.dto.UserResponseDto;
import com.programmers.jpaboard.domain.user.entity.User;
import com.programmers.jpaboard.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	public UserResponseDto createUser(UserCreateRequestDto userCreateRequestDto) {
		User user = UserConverter.toUser(userCreateRequestDto);
		User savedUser = userRepository.save(user);
		UserResponseDto userResponseDto = UserConverter.toUserResponseDto(savedUser);
		return userResponseDto;
	}
}
