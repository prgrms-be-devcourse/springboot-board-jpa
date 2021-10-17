package com.devcourse.springbootboard.user.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import lombok.Getter;

@Getter
public class UserUpdateRequest {
	@NotBlank(message = "이름을 입력해볼까요?")
	private final String name;

	@Positive(message = "1 이상의 숫자로 입력해볼까요?")
	private final int age;

	private final String hobby;

	public UserUpdateRequest(String name, int age, String hobby) {
		this.name = name;
		this.age = age;
		this.hobby = hobby;
	}
}
