package com.programmers.springboard.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record MemberUpdateRequest(
	@NotBlank(message = "비밀번호를 입력하세요")
	@Size(max = 30, message = "비밀번호는 30자 미만입니다.")
	String password
) {
}
