package com.prgrms.devcourse.springjpaboard.domain.user.service;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import com.prgrms.devcourse.springjpaboard.domain.user.User;
import com.prgrms.devcourse.springjpaboard.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	public void create(User user) {
		userRepository.save(user);
	}

	public User findById(Long userId) {
		return userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
	}

}
