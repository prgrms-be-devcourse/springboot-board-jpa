package com.kdt.jpa.domain.member.dto;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public record MemberResponse(
	Long id,
	String name,
	int age,
	String hobby,
	LocalDateTime createdAt
) {

	public record JoinMemberResponse(Long id) {
	}

	public record UpdateMemberResponse(
		String name,
		int age,
		String hobby
	) {
	}
}
