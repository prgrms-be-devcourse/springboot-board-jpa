package com.programmers.heheboard.domain.user;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserResponseDTO {
	private String name;

	private int age;

	private String hobby;

	private LocalDateTime createdAt;

	private LocalDateTime modifiedAt;

	@Builder
	public UserResponseDTO(String name, int age, String hobby, LocalDateTime createdAt, LocalDateTime modifiedAt) {
		this.name = name;
		this.age = age;
		this.hobby = hobby;
		this.createdAt = createdAt;
		this.modifiedAt = modifiedAt;
	}

	public static UserResponseDTO toResponse(User user) {
		return UserResponseDTO.builder()
			.name(user.getName())
			.age(user.getAge())
			.hobby(user.getHobby())
			.createdAt(user.getCreatedAt())
			.modifiedAt(user.getModifiedAt())
			.build();
	}
}
