package com.prgrms.devcourse.springjpaboard.domain.user.api.dto;

import com.prgrms.devcourse.springjpaboard.domain.user.User;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserRequestDto {

	@NotBlank
	private String name;

	@NotBlank
	private Integer age;

	@NotBlank
	private String hobby;

	@Builder
	public UserRequestDto(String name, Integer age, String hobby) {
		this.name = name;
		this.age = age;
		this.hobby = hobby;
	}

	public User toUser() {
		return User.builder()
			.name(this.name)
			.age(this.age)
			.hobby(this.hobby)
			.build();
	}
}
