package com.programmers.heheboard.domain.user;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public record UserResponseDto(String name, int age, String hobby, LocalDateTime createdAt, LocalDateTime modifiedAt) {

	public static UserResponseDto toResponse(User user) {
		return UserResponseDto.builder()
			.name(user.getName())
			.age(user.getAge())
			.hobby(user.getHobby())
			.createdAt(user.getCreatedAt())
			.modifiedAt(user.getModifiedAt())
			.build();
	}
}
