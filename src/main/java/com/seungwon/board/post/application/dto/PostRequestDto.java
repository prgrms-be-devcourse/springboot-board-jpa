package com.seungwon.board.post.application.dto;

import lombok.Builder;

@Builder
public record PostRequestDto(
		String content,
		String title,
		Long writerId

) {
}
