package com.devcourse.springbootboard.user.dto;

import lombok.Getter;

@Getter
public class UserUpdateRequest {
	private String name;
	private int age;
	private String hobby;
}
