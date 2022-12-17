package com.programmers.jpaboard.domain.post.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostCreateRequestDto {

	@NotBlank(message = "게시글 제목은 작성되어야합니다.")
	@Size(max = 100, message = "제목이 최대 길이를 초과합니다.")
	private String title;

	@NotNull(message = "게시글 내용은 작성되어야합니다.")
	private String content;

	@NotNull(message = "사용자 아이디가 없습니다.")
	@Positive(message = "잘못된 사용자 아이디입니다.")
	private Long userId;
}
