package org.programmers.kdtboard.converter;

import org.programmers.kdtboard.domain.post.Post;
import org.programmers.kdtboard.domain.user.User;
import org.programmers.kdtboard.dto.PostDto.PostResponse;
import org.programmers.kdtboard.dto.UserDto.UserResponseDto;
import org.springframework.stereotype.Component;

@Component
public class Converter {

	public PostResponse postConverter(Post post) {
		return new PostResponse(post.getId(), post.getTitle(), post.getContent(),
			post.getCreatedAt(), post.getCreatedBy(), post.getUser().getId());
	}

	public UserResponseDto userConverter(User user) {
		return new UserResponseDto(user.getId(), user.getName(), user.getAge(), user.getHobby(), user.getCreatedAt(),
			user.getCreatedBy());
	}

	public User userDtoConverter(UserResponseDto userResponseDto) {
		return User.create(userResponseDto.id(), userResponseDto.name(), userResponseDto.age(), userResponseDto.hobby(),
			userResponseDto.createdAt(), userResponseDto.createdBy());
	}
}
