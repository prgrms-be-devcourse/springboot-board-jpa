package com.programmers.heheboard.domain.user;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;

	public UserResponseDTO createUser(CreateUserRequestDto createUserRequestDto) {
		User user = createUserRequestDto.toEntity();

		return UserResponseDTO.toResponse(userRepository.save(user));
	}
}
