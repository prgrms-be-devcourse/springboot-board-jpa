package com.prgrms.boardjpa.user;

import org.springframework.stereotype.Service;

import com.prgrms.boardjpa.post.NotExistException;
import com.prgrms.boardjpa.user.dto.UserDto;

@Service
public class UserService {
	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public UserDto.Info create(UserDto.CreateRequest request) {
		User user = createUser(request);

		userRepository.save(user);

		return new UserDto.Info(
			user.getId(),
			user.getEmail(),
			user.getHobby(),
			user.getName(),
			user.getAge()
		);
	}

	private User createUser(UserDto.CreateRequest createRequest) {
		return User.builder()
			.email(createRequest.email())
			.hobby(createRequest.hobby())
			.age(createRequest.age())
			.name(createRequest.name())
			.password(createRequest.password())
			.build();
	}

	public User getById(Long writerId) {
		return userRepository.findById(writerId)
			.orElseThrow(NotExistException::new);
	}
}
