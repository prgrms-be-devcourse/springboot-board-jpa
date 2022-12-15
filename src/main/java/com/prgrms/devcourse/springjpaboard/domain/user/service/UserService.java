package com.prgrms.devcourse.springjpaboard.domain.user.service;

import org.springframework.stereotype.Service;

import com.prgrms.devcourse.springjpaboard.domain.user.User;
import com.prgrms.devcourse.springjpaboard.domain.user.api.dto.UserRequestDto;
import com.prgrms.devcourse.springjpaboard.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	public void create(UserRequestDto userRequestDto) {
		userRepository.save(userRequestDto.toUser());
	}

	public User findById(Long userId) {
		return userRepository.findById(userId).orElseThrow();
	}

}
