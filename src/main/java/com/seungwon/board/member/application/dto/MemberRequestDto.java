package com.seungwon.board.member.application.dto;

public record MemberRequestDto(
		String name,
		int age,
		String hobby
) {
}
