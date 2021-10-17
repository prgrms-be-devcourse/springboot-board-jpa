package com.devcourse.springbootboard.post.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.Getter;

@Getter
public class PostUpdateRequest {
	@Positive
	private final Long postId;

	@NotBlank(message = "제목을 입력해볼까요?")
	@Size(min = 1, max = 20, message = "제목은 1~20자로 작성해볼까요?")
	private final String title;

	@NotBlank(message = "내용을 입력해볼까요?")
	@Size(min = 1, max = 200, message = "내용은 1~200자로 작성해볼까요?")
	private final String content;

	@Positive
	private final Long userId;

	public PostUpdateRequest(Long postId, String title, String content, Long userId) {
		this.postId = postId;
		this.title = title;
		this.content = content;
		this.userId = userId;
	}
}
