package com.programmers.springboard.request;

import com.programmers.springboard.entity.Member;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record MemberCreateRequest(
	@NotBlank(message = "이름을 입력하세요")
	@Size(max = 30, message = "이름은 30자 미만입니다.")
	String name,

	@NotBlank(message = "아아디를 입력하세요")
	@Size(max = 30, message = "아이디는 30자 미만입니다.")
	String loginId,

	@NotBlank(message = "비밀번호를 입력하세요")
	@Size(max = 30, message = "비밀번호는 30자 미만입니다.")
	String password
) {
	public Member toEntity(String encodedPassword) {
		return new Member(name, loginId, encodedPassword);
	}
}
