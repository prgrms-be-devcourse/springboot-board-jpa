package com.devcourse.springbootboard.user.dto;

import lombok.Getter;

@Getter
public class UserDeleteResponse {
	private final Long id;

	public UserDeleteResponse(Long id) {
		this.id = id;
	}
}
