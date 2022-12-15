package com.prgrms.devcourse.springjpaboard.domain.user.service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserSaveDto {

	@NotBlank
	private String name;

	@NotBlank
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
