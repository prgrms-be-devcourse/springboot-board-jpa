package com.programmers.heheboard.domain.user;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public final class CreateUserRequestDto {

	@NotBlank
	private final String name;

	@Min(0)
	@Max(100)
	private final int age;

	@NotBlank
	private final String hobby;

	public CreateUserRequestDto(String name, int age, String hobby) {
		this.name = name;
		this.age = age;
		this.hobby = hobby;
	}

	public User toEntity() {
		return User.builder()
			.name(name)
			.age(age)
			.hobby(hobby)
			.build();
	}

	public String name() {
		return name;
	}

	public int age() {
		return age;
	}

	public String hobby() {
		return hobby;
	}
}
