package com.kdt.jpa.domain.member.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public record MemberRequest() {

	public record JoinMemberRequest(
		@Size(min = 2, max = 25)
		@NotBlank
		String name,

		@NotNull
		@Min(1)
		int age,

		@Size(max = 16)
		String hobby) {
	}

	public record UpdateMemberRequest(
		@Size(min = 2, max = 25)
		@NotBlank
		String name,

		@NotNull
		@Min(1)
		int age,

		@Size(max = 16)
		String hobby) {
	}
}
