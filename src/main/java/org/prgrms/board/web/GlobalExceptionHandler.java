package org.prgrms.board.web;

import static java.time.LocalDateTime.*;
import static org.prgrms.board.web.ApiResponse.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

	private ResponseEntity<ApiResponse<?>> newResponse(Throwable throwable, HttpStatus status) {
		return new ResponseEntity<>(error(now(), throwable), status);
	}

	@ExceptionHandler({IllegalStateException.class, IllegalArgumentException.class})
	public ResponseEntity<ApiResponse<?>> handlerBadRequestException(Exception e) {
		log.debug("Bad request exception occurred: {}", e.getMessage(), e);
		return newResponse(e, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({Exception.class, RuntimeException.class})
	public ResponseEntity<ApiResponse<?>> handleAllException(Exception e) {
		log.error("Unexpected exception occurred: {}", e.getMessage(), e);
		return newResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
