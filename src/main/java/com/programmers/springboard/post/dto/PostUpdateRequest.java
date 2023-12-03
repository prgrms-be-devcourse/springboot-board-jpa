package com.programmers.springboard.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PostUpdateRequest(
	@Size(max = 60, message = "제목은 60자 미만입니다")
	String title,
	@NotBlank(message = "내용을 입력해주세요")
	String content
) {
}
