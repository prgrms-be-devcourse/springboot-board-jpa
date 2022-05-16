package org.programmers.kdtboard.dto;

import java.time.LocalDateTime;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserDto {

	@Valid
	public record UserCreateDto(
		@NotBlank @Size(max = 10)
		String name,
		int age,
		String hobby) {
	}

	public record UserResponseDto(
		Long id,
		String name,
		int age,
		String hobby,
		LocalDateTime createdAt,
		String createdBy) {
	}
}

