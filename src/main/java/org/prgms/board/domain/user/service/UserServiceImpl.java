package org.prgms.board.domain.user.service;

import javax.transaction.Transactional;

import org.prgms.board.domain.user.domain.User;
import org.prgms.board.domain.user.domain.UserRepository;
import org.prgms.board.domain.user.dto.UserDto;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	@Override
	public UserDto.Response saveUser(UserDto.Save saveDto) {
		final User savedUser = userRepository.save(User.create(saveDto.getName(), saveDto.getAge()));

		return UserDto.Response.toUserResponse(savedUser);
	}

	@Override
	public UserDto.Response getUser(Long userId) {
		final User findUser = userRepository.findById(userId)
			.orElseThrow(() -> new IllegalStateException("해당하는 사용자가 존재하지 않습니다. userId : " + userId));

		return UserDto.Response.toUserResponse(findUser);
	}

	@Override
	public void updateUser(UserDto.Update updateDto) {
		final User findUser = userRepository.findById(updateDto.getUserId())
			.orElseThrow(() -> new IllegalStateException("해당하는 사용자가 존재하지 않습니다. userId : " + updateDto.getUserId()));

		findUser.updateUser(updateDto.getName(), updateDto.getAge());
	}

	@Override
	public void deleteUser(Long userId) {
		userRepository.deleteById(userId);
	}
}
