package com.programmers.springboard.response;

import com.programmers.springboard.entity.Member;

public record MemberResponse(Long id, String name, int age, String hobby) {
	public static MemberResponse of(Member member) {
		return new MemberResponse(member.getId(), member.getName(), member.getAge(), member.getHobby());
	}
}
