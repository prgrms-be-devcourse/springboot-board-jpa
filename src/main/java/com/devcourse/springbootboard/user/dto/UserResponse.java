package com.devcourse.springbootboard.user.dto;

import com.devcourse.springbootboard.user.domain.Hobby;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserResponse {
	private final Long id;
	private final String name;
	private final int age;
	private final Hobby hobby;

	@Builder
	public UserResponse(Long id, String name, int age, Hobby hobby) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.hobby = hobby;
	}
}
