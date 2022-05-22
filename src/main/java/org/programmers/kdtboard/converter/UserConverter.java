package org.programmers.kdtboard.converter;

import org.programmers.kdtboard.domain.user.User;
import org.programmers.kdtboard.dto.UserDto.UserResponseDto;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {
	public UserResponseDto convertUserResponse(User user) {
		return new UserResponseDto(user.getId(), user.getName(), user.getAge(), user.getHobby(),
			user.getCreatedAt(),
			user.getCreatedBy());
	}

	public User convertUser(UserResponseDto userResponseDto) {
		var user = User.builder()
			.id(userResponseDto.id())
			.name(userResponseDto.name())
			.age(userResponseDto.age())
			.hobby(userResponseDto.hobby())
			.build();
		user.setCreatedAt(userResponseDto.createdAt());
		user.setCreatedBy(userResponseDto.createdBy());

		return user;
	}
}
