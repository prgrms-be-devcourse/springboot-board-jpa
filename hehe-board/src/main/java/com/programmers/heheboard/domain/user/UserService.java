package com.programmers.heheboard.domain.user;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;

	public UserResponseDto createUser(CreateUserRequestDto createUserRequestDto) {
		User user = createUserRequestDto.toEntity();

		return UserResponseDto.toResponse(userRepository.save(user));
	}
}
