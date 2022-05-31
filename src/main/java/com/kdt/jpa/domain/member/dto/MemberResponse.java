package com.kdt.jpa.domain.member.dto;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public class MemberResponse {
	private Long id;
	private String name;
	private int age;
	private String hobby;

	private LocalDateTime createdAt;

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}

	public String getHobby() {
		return hobby;
	}

	public MemberResponse(Long id, String name, int age, String hobby, LocalDateTime createdAt) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.hobby = hobby;
		this.createdAt = createdAt;
	}

	public static class JoinMemberResponse {
		private Long id;

		public Long getId() {
			return id;
		}

		public JoinMemberResponse(Long id) {
			this.id = id;
		}
	}

	public static class UpdateMemberResponse {
		private String name;
		private int age;
		private String hobby;

		public String getName() {
			return name;
		}

		public int getAge() {
			return age;
		}

		public String getHobby() {
			return hobby;
		}

		public UpdateMemberResponse(String name, int age, String hobby) {
			this.name = name;
			this.age = age;
			this.hobby = hobby;
		}
	}
}
