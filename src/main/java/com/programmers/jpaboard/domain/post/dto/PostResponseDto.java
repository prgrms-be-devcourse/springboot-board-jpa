package com.programmers.jpaboard.domain.post.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostResponseDto {

	private Long id;
	private String title;
	private String content;
	private Long userId;
	private LocalDateTime createdAt;
	private LocalDateTime lastModifiedAt;
}
