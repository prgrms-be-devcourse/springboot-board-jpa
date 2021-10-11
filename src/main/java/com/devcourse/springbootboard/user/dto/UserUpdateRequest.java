package com.devcourse.springbootboard.user.dto;

import lombok.Getter;

@Getter
public class UserUpdateRequest {
	private String name;
	private int age;
	private String hobby;

	public UserUpdateRequest(String name, int age, String hobby) {
		this.name = name;
		this.age = age;
		this.hobby = hobby;
	}
}
