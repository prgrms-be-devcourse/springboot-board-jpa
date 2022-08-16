package com.prgrms.boardjpa.application.user;

import org.springframework.stereotype.Component;

import com.prgrms.boardjpa.application.user.model.User;

@Component
public class UserConverter {
	public User toEntity(UserDto.CreateRequest createRequest) {
		return User.builder()
			.name(createRequest.name())
			.age(createRequest.age())
			.password(createRequest.password())
			.hobby(createRequest.hobby())
			.email(createRequest.email())
			.build();
	}

	public UserDto.Info from(User user) {
		return new UserDto.Info(
			user.getId(),
			user.getEmail(),
			user.getHobby(),
			user.getName(),
			user.getAge()
		);
	}
}
