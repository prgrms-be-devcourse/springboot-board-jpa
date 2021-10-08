package com.devcourse.springbootboard.user.converter;

import com.devcourse.springbootboard.user.domain.Hobby;
import com.devcourse.springbootboard.user.domain.User;
import com.devcourse.springbootboard.user.dto.UserResponse;
import com.devcourse.springbootboard.user.dto.UserSignUpRequest;

public class UserConverter {
	private UserConverter() {
	}

	public static UserResponse toUserResponse(User user) {
		return UserResponse.builder()
			.id(user.getId())
			.name(user.getName())
			.age(user.getAge())
			.hobby(user.getHobby())
			.build();
	}

	public static User convertUserSignUpRequest(UserSignUpRequest userSignUpRequest) {
		return User.builder()
			.name(userSignUpRequest.getName())
			.age(userSignUpRequest.getAge())
			.hobby(new Hobby(userSignUpRequest.getHobby()))
			.build();
	}
}
