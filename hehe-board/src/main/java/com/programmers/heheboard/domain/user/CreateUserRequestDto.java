package com.programmers.heheboard.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateUserRequestDto {

	private String name;

	private int age;

	private String hobby;

	public User toEntity() {
		return User.builder()
			.name(name)
			.age(age)
			.hobby(hobby)
			.build();
	}
}
