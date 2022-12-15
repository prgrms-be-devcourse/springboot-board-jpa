package com.prgrms.devcourse.springjpaboard.domain.post.service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostSaveDto {

	@NotBlank
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
