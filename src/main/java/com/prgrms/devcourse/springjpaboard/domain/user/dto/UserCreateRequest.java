package com.prgrms.devcourse.springjpaboard.domain.user.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserCreateRequest {

	@NotBlank(message = "이름을 입력해 주세요")
	private String name;

	@NotNull(message = "나이를 입력해 주세요")
	private Integer age;

	@NotBlank(message = "취미를 입력해 주세요")
	private String hobby;

	public UserCreateRequest(String name, Integer age, String hobby) {
		this.name = name;
		this.age = age;
		this.hobby = hobby;
	}

}
