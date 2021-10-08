package com.devcourse.springbootboard.post.dto;

import lombok.Getter;

@Getter
public class PostWriteRequest {
	private String title;
	private String content;
	private Long userId;
}
