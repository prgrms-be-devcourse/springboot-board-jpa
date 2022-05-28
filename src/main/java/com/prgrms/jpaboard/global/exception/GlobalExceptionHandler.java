package com.prgrms.jpaboard.global.exception;

import com.prgrms.jpaboard.domain.post.exception.PostNotFoundException;
import com.prgrms.jpaboard.domain.user.exception.UserNotFoundException;
import com.prgrms.jpaboard.global.common.response.ErrorResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // RequestBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        final ErrorResponseDto errorResponse = ErrorResponseDto.multipleError(ErrorCode.BAD_REQUEST, e.getBindingResult());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    // ModelAttribute
    @ExceptionHandler(org.springframework.validation.BindException.class)
    public ResponseEntity<ErrorResponseDto> handleBindException(BindException e) {
        final ErrorResponseDto errorResponse = ErrorResponseDto.multipleError(ErrorCode.BAD_REQUEST, e.getBindingResult());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleUserNotFundException(UserNotFoundException e) {
        final ErrorResponseDto errorResponseDto = ErrorResponseDto.singleError(ErrorCode.USER_NOT_FOUND);

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorResponseDto);
    }

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handlePostNotFoundException(PostNotFoundException e) {
        final ErrorResponseDto errorResponseDto = ErrorResponseDto.singleError(ErrorCode.POST_NOT_FOUND);

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorResponseDto);
    }
}
