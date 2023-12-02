package com.programmers.springboard.response;

import java.util.List;

public record MemberLoginResponse(
	String token,
	Long memberId,
	List<String> group
) {
}
