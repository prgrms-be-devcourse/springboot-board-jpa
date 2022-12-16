package com.programmers.jpaboard.domain.user.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.Getter;

@Getter
public class UserRequestDto {

	@NotBlank
	@Size(max = 30)
	private String name;

	@NotEmpty
	@Email
	private String email;

	@Positive
	@Max(200)
	private int age;

	@Size(max = 20)
	private String hobby;
}
