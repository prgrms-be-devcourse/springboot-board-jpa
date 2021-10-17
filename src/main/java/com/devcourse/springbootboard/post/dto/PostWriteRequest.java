package com.devcourse.springbootboard.post.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.Getter;

@Getter
public class PostWriteRequest {
	@NotBlank(message = "제목을 입력해볼까요?")
	@Size(min = 1, max = 20, message = "제목은 1~20자로 작성해볼까요?")
	private final String title;

	@NotBlank(message = "내용을 입력해볼까요?")
	@Size(min = 1, max = 200, message = "내용은 1~200자로 작성해볼까요?")
	private final String content;

	@NotNull(message = "꼭 입력해주셔야해요!")
	@Positive(message = "0보다 큰 값을 입력해주시겠어요?")
	private final Long userId;

	public PostWriteRequest(String title, String content, Long userId) {
		this.title = title;
		this.content = content;
		this.userId = userId;
	}
}
