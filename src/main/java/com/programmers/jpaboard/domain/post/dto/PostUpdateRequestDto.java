package com.programmers.jpaboard.domain.post.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PostUpdateRequestDto {

	@NotBlank(message = "게시글 제목은 작성되어야합니다.")
	@Size(max = 100, message = "제목이 최대 길이를 초과합니다.")
	private String title;

	@NotNull(message = "게시글 내용은 작성되어야합니다.")
	private String content;
}
