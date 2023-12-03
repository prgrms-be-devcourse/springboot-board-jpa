package com.programmers.springboard.response;

public record MemberLoginResponse(
	String token,
	Long memberId,
	String role
) {
}
