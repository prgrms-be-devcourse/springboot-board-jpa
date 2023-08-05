package com.programmers.heheboard.domain.user;

public record CreateUserRequestDto(String name, int age, String hobby) {

	public User toEntity() {
		return User.builder()
			.name(name)
			.age(age)
			.hobby(hobby)
			.build();
	}
}
