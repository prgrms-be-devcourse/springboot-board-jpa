package com.kdt.jpa.domain.member;

import org.springframework.stereotype.Component;

import com.kdt.jpa.domain.member.dto.MemberRequest;
import com.kdt.jpa.domain.member.dto.MemberResponse;
import com.kdt.jpa.domain.member.model.Member;

@Component
public class MemberConverter {

	public Member toEntity(MemberResponse memberResponse) {
		Member member = Member.builder()
			.id(memberResponse.id())
			.name(memberResponse.name())
			.age(memberResponse.age())
			.hobby(memberResponse.hobby())
			.build();
		member.setCreatedAt(memberResponse.createdAt());
		return member;
	}

	public Member toEntity(MemberRequest.JoinMemberRequest request) {
		return Member.generateNewMemberInstance(request.name(), request.age(), request.hobby());

	}

	public MemberResponse toMemberResponse(Member member) {
		return MemberResponse.builder()
			.id(member.getId())
			.name(member.getName())
			.age(member.getAge())
			.hobby(member.getHobby())
			.build();
	}

	public MemberResponse.JoinMemberResponse toJoinMemberResponse(Member member) {
		return new MemberResponse.JoinMemberResponse(member.getId());
	}
}
