package com.programmers.jpaboard.web.post;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.programmers.jpaboard.common.exception.PostNotFoundException;
import com.programmers.jpaboard.web.common.ExceptionResult;

@RestControllerAdvice(assignableTypes = PostApiController.class)
public class PostControllerAdvice {

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler
	public ExceptionResult notFoundHandler(PostNotFoundException e) {
		return new ExceptionResult(e.getMessage(), LocalDateTime.now());
	}
}
