package com.prgrms.devcourse.springjpaboard.domain.user.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserSaveDto {

	@NotBlank
	private String name;

	@NotNull
	private Integer age;

	@NotBlank
	private String hobby;

	@Builder
	public UserSaveDto(String name, Integer age, String hobby) {
		this.name = name;
		this.age = age;
		this.hobby = hobby;
	}

}
