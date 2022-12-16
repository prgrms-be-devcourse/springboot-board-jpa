package com.programmers.jpaboard.web.post.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;

@Getter
public class PostUpdateRequestDto {

	@NotBlank
	@Size(max = 100)
	private String title;

	@NotNull
	private String content;
}
