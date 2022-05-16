package com.programmers.board.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalHandling {

	private final Logger log = LoggerFactory.getLogger(GlobalHandling.class);

	private static final String CLIENT_MESSAGE="잘못된 값을 입력했습니다.";
	private static final String INTERNAL_MESSAGE="내부적으로 문제가 발생했습니다.";

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ServiceException.NotFoundResource.class)
	public String handleNotFoundResourceException(ServiceException.NotFoundResource e) {
		log.warn("해당 도메인을 찾을 수 없습니다. {} ", e.getMessage());
		return CLIENT_MESSAGE;
	}

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(DomainException.ConstraintException.class)
	public String handleConstraintException(DomainException.ConstraintException e) {
		log.warn("도메인을 생성할 제약 조건을 위반하였습니다. {} ", e.getMessage());
		return CLIENT_MESSAGE;
	}

}
