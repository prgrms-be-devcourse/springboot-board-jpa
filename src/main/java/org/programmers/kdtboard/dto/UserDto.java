package org.programmers.kdtboard.dto;

import lombok.Builder;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserDto {
	public record CreateRequest(
		@NotBlank @Size(max = 10)
		String name,
		int age,
		@NotBlank @Size(max = 30)
		String hobby) {
	}

	@Builder
	public record Response(
		Long id,
		String name,
		int age,
		String hobby,
		LocalDateTime createdAt,
		String createdBy) {
	}
}
