package com.programmers.springboard.request;

import jakarta.validation.constraints.NotBlank;

public record PostSearchRequest(
	@NotBlank(message = "검색어를 입력해주세요")
	String title
) {
}
