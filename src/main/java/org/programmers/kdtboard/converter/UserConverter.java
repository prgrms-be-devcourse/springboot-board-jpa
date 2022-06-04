package org.programmers.kdtboard.converter;

import org.programmers.kdtboard.domain.user.User;
import org.programmers.kdtboard.dto.UserDto.Response;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {
	public Response convertUserResponse(User user) {
		return new Response(user.getId(), user.getName(), user.getAge(), user.getHobby(),
			user.getCreatedAt(),
			user.getCreatedBy());
	}
}
