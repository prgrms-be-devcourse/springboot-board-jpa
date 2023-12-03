package com.programmers.springboard.global.common;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.programmers.springboard.global.common.ApiResponse;
import com.programmers.springboard.global.common.CustomException;
import com.programmers.springboard.global.common.ErrorInfo;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
		HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		logger.info("Method argument not valid : ", ex);

		List<String> errorMessages = ex.getBindingResult()
			.getFieldErrors()
			.stream()
			.map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
			.toList();
		ApiResponse<ErrorInfo> apiResponse = new ApiResponse<>("ERR400", ErrorInfo.of(errorMessages.toString()));
		return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ApiResponse<ErrorInfo>> handleCustomException(CustomException ex) {
		logger.warn("Custom exception occurred : ", ex);
		ApiResponse<ErrorInfo> apiResponse = new ApiResponse<>(ex.getCode(), ErrorInfo.of(ex.getMessage()));
		return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
		HttpStatusCode statusCode, WebRequest request) {
		logger.error("Exception occurred : ", ex);
		ApiResponse<ErrorInfo> apiResponse = new ApiResponse<>("ERR500", ErrorInfo.of(ex.getMessage()));
		return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
