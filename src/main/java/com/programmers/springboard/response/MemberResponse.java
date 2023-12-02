package com.programmers.springboard.response;

import com.programmers.springboard.entity.Member;
import com.programmers.springboard.entity.Role;

public record MemberResponse(
	Long id,
	String name,
	String loginId,
	Role role
) {
	public static MemberResponse of(Member member) {
		return new MemberResponse(
			member.getId(),
			member.getName(),
			member.getLoginId(),
			member.getRole());
	}
}
