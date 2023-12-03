package com.programmers.springboard.member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record MemberLoginRequest(
	@Size(max = 30, message = "loginId는 최대 30글자입니다.")
	@NotBlank(message = "loginId가 비어있으면 안됩니다.")
	String loginId,

	@Size(max = 30, message = "loginId는 최대 30글자입니다.")
	@NotBlank(message = "비밀번호가 비어있으면 안됩니다.")
	String password
) {
}
