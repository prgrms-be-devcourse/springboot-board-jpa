package org.prgms.board.domain.user.service;

import javax.transaction.Transactional;

import org.prgms.board.domain.user.domain.User;
import org.prgms.board.domain.user.domain.UserRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	@Override
	public User saveUser(String name, int age) {
		return userRepository.save(User.create(name, age));
	}

	@Override
	public User findUser(Long userId) {
		return userRepository.findById(userId)
			.orElseThrow(() -> new IllegalStateException("해당하는 사용자가 존재하지 않습니다. userId : " + userId));
	}

	@Override
	public void updateUser(String name, int age, Long userId) {
		final User findUser = userRepository.findById(userId)
			.orElseThrow(() -> new IllegalStateException("해당하는 사용자가 존재하지 않습니다. userId : " + userId));

		findUser.updateUser(name, age);
	}

	@Override
	public void deleteUser(Long userId) {
		userRepository.deleteById(userId);
	}
}
