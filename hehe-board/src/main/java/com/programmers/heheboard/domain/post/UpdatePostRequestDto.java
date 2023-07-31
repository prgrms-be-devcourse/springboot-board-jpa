package com.programmers.heheboard.domain.post;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdatePostRequestDto {
	private String title;
	private String content;
}
