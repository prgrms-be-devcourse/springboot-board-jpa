package com.prgrms.devcourse.springjpaboard.domain.user.application.converter;

import org.springframework.stereotype.Component;

import com.prgrms.devcourse.springjpaboard.domain.user.User;
import com.prgrms.devcourse.springjpaboard.domain.user.dto.UserCreateRequest;
import com.prgrms.devcourse.springjpaboard.domain.user.dto.UserCreateResponse;

@Component
public class UserConverter {

	public User toUser(UserCreateRequest userCreateRequest) {
		return User.builder()
			.name(userCreateRequest.getName())
			.age(userCreateRequest.getAge())
			.hobby(userCreateRequest.getHobby())
			.build();
	}

	public UserCreateResponse toUserCreateResponse(Long id) {
		return new UserCreateResponse(id);
	}

}
