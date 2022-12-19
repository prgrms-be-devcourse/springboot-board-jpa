package com.prgrms.devcourse.springjpaboard.domain.user.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.devcourse.springjpaboard.domain.user.User;
import com.prgrms.devcourse.springjpaboard.domain.user.exeception.UserNotFoundException;
import com.prgrms.devcourse.springjpaboard.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

	private final UserRepository userRepository;

	@Transactional
	public void create(User user) {
		userRepository.save(user);
	}

	public User findById(Long userId) {
		return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
	}

}
