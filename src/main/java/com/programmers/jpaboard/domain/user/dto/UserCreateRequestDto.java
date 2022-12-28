package com.programmers.jpaboard.domain.user.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserCreateRequestDto {

	@NotBlank(message = "사용자 이름을 작성되어야합니다.")
	@Size(max = 30, message = "사용자 이름이 최대 길이를 초과합니다.")
	private String name;

	@NotEmpty(message = "사용자 email 은 작성되어야합니다.")
	@Email(message = "잘못된 email 형식입니다.")
	private String email;

	@Positive(message = "나이는 양수여야합니다.")
	@Max(value = 200)
	private int age;

	@Size(max = 20, message = "사용자 취미는 20자를 넘기면 안됩니다.")
	private String hobby;
}
