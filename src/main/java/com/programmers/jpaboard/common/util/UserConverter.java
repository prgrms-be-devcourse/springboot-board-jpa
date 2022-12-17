package com.programmers.jpaboard.common.util;

import com.programmers.jpaboard.domain.post.entity.Post;
import com.programmers.jpaboard.domain.user.dto.UserCreateRequestDto;
import com.programmers.jpaboard.domain.user.dto.UserResponseDto;
import com.programmers.jpaboard.domain.user.entity.User;

public class UserConverter {

	public static User toUser(UserCreateRequestDto userCreateRequestDto) {
		return new User(
			userCreateRequestDto.getName(),
			userCreateRequestDto.getEmail(),
			userCreateRequestDto.getAge(),
			userCreateRequestDto.getHobby()
		);
	}

	public static UserResponseDto toUserResponseDto(User user) {
		return new UserResponseDto(
			user.getId(),
			user.getName(),
			user.getEmail(),
			user.getAge(),
			user.getHobby(),
			user.getPosts().stream()
				.map(Post::getId)
				.toList()
		);
	}
}
