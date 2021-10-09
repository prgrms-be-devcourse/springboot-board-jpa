package com.devcourse.springbootboard.user.converter;

import org.springframework.stereotype.Component;

import com.devcourse.springbootboard.user.domain.Hobby;
import com.devcourse.springbootboard.user.domain.User;
import com.devcourse.springbootboard.user.dto.DeleteResponse;
import com.devcourse.springbootboard.user.dto.UserResponse;
import com.devcourse.springbootboard.user.dto.UserSignUpRequest;

@Component
public class UserConverter {
	public UserResponse toUserResponse(User user) {
		return UserResponse.builder()
			.id(user.getId())
			.name(user.getName())
			.age(user.getAge())
			.hobby(user.getHobby())
			.build();
	}

	public User convertUserSignUpRequest(UserSignUpRequest userSignUpRequest) {
		return User.builder()
			.name(userSignUpRequest.getName())
			.age(userSignUpRequest.getAge())
			.hobby(new Hobby(userSignUpRequest.getHobby()))
			.build();
	}

	public DeleteResponse toDeleteResponse(Long id) {
		return new DeleteResponse(id);
	}
}
