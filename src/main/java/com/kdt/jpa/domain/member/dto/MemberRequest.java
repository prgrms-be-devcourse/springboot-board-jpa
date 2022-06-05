package com.kdt.jpa.domain.member.dto;

public record MemberRequest() {

	public record JoinMemberRequest(String name, int age, String hobby){
	}

	public record UpdateMemberRequest (String name, int age, String hobby){

	}
}
