package com.prgrms.devcourse.springjpaboard.domain.post.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostRequestDto {

	@NotBlank
	private String title;

	@NotBlank
	private String content;

	@Builder
	public PostRequestDto(String title, String content) {
		this.title = title;
		this.content = content;
	}
}
