package com.prgrms.devcourse.springjpaboard.domain.post.application.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostSaveDto {

	@NotNull
	private Long userId;

	@NotBlank
	private String title;

	@NotBlank
	private String content;

	@Builder
	public PostSaveDto(Long userId, String title, String content) {
		this.userId = userId;
		this.title = title;
		this.content = content;
	}

}
