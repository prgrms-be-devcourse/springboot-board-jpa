package com.programmers.springbootboard.error;

import com.programmers.springbootboard.error.exception.DuplicationArgumentException;
import com.programmers.springbootboard.error.exception.InvalidArgumentException;
import com.programmers.springbootboard.error.exception.InvalidMediaTypeException;
import com.programmers.springbootboard.error.exception.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // TODO 중복된 로직을 빼보자!! --> enum으로 진행하자!!! 그럼 exception 코드 불필요한거 지울 수 있음!! exception을 instanceof로 찾아내서 ㄲ
    @ExceptionHandler(InvalidArgumentException.class)
    protected ResponseEntity<ErrorResponseDto> handleInvalidArgumentException(InvalidArgumentException exception) {
        // 중복된 로직을 빼보자!!
        ErrorMessage errorMessage = ErrorMessage.of(exception.getMessage());
        ErrorResponseDto response = ErrorResponseDto.of(errorMessage);
        return new ResponseEntity<>(response, errorMessage.getStatus());
    }

    @ExceptionHandler(DuplicationArgumentException.class)
    protected ResponseEntity<ErrorResponseDto> handleDuplicationArgumentException(DuplicationArgumentException exception) {
        // 중복된 로직을 빼보자!!
        ErrorMessage errorMessage = ErrorMessage.of(exception.getMessage());
        ErrorResponseDto response = ErrorResponseDto.of(errorMessage);
        return new ResponseEntity<>(response, errorMessage.getStatus());
    }

    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<ErrorResponseDto> handleNotFoundException(NotFoundException exception) {
        // 중복된 로직을 빼보자!!
        ErrorMessage errorMessage = ErrorMessage.of(exception.getMessage());
        ErrorResponseDto response = ErrorResponseDto.of(errorMessage);
        return new ResponseEntity<>(response, errorMessage.getStatus());
    }

    // TODO
    @ExceptionHandler(InvalidMediaTypeException.class)
    protected ResponseEntity<ErrorResponseDto> handleInvalidMediaTypeException() {
        ErrorMessage errorMessage = ErrorMessage.UNSUPPORTED_MEDIA_TYPE;
        ErrorResponseDto response = ErrorResponseDto.of(errorMessage);
        return new ResponseEntity<>(response, errorMessage.getStatus());
    }


}
