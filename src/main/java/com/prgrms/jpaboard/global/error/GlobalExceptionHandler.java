package com.prgrms.jpaboard.global.error;

import com.prgrms.jpaboard.domain.post.exception.PostNotFoundException;
import com.prgrms.jpaboard.domain.user.exception.UserNotFoundException;
import com.prgrms.jpaboard.global.common.response.ErrorResponseDto;
import com.prgrms.jpaboard.global.error.exception.WrongFieldException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // RequestBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        final ErrorResponseDto errorResponseDto = ErrorResponseDto.multipleError(ErrorCode.BAD_REQUEST, e.getBindingResult());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponseDto);
    }

    // ModelAttribute
    @ExceptionHandler(org.springframework.validation.BindException.class)
    public ResponseEntity<ErrorResponseDto> handleBindException(BindException e) {
        final ErrorResponseDto errorResponseDto = ErrorResponseDto.multipleError(ErrorCode.BAD_REQUEST, e.getBindingResult());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponseDto);
    }

    @ExceptionHandler(WrongFieldException.class)
    public ResponseEntity<ErrorResponseDto> handleWrongFieldException(WrongFieldException e) {
        final ErrorResponseDto errorResponseDto = ErrorResponseDto.singleError(ErrorCode.BAD_REQUEST, e);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponseDto);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDto> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        final ErrorResponseDto errorResponseDto = ErrorResponseDto.singleError(ErrorCode.BAD_REQUEST, e.getMessage());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponseDto);
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
