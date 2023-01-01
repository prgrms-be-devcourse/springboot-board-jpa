package com.programmers.jpaboard.web.common;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExceptionResult {

	private String message;
	private LocalDateTime createdAt;
}
