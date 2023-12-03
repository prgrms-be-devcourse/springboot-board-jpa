package com.programmers.springboard.member.dto;

import com.programmers.springboard.member.domain.Member;
import com.programmers.springboard.member.domain.Role;

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
