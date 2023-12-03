package com.programmers.springboard.member.dto;

public record MemberLoginResponse(
	String token,
	Long memberId,
	String role
) {
}
