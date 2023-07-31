package com.prgrms.board.domain.user.service;

import static com.prgrms.board.global.common.ErrorCode.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.board.domain.user.entity.User;
import com.prgrms.board.domain.user.exception.UserNotFoundException;
import com.prgrms.board.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

	private final UserRepository userRepository;

	public User findUserOrThrow(Long userId) {
		return userRepository.findById(userId)
			.orElseThrow(() -> new UserNotFoundException(NO_USER));
	}
}
