package com.programmers.springbootboardjpa.exception;

import com.programmers.springbootboardjpa.dto.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * @Valid에 의한 검증에 대한 예외처리
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response<String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Response.fail(HttpStatus.BAD_REQUEST, e.getBindingResult().toString(), e.getMessage()));
    }

    /**
     * IllegalArgumentException에 대한 예외처리
     * 대부분 Entity에서 Assert에 의한 값 검증에서 발생
     * @param e
     * @return
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Response<String>> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Response.fail(HttpStatus.BAD_REQUEST, e.getMessage(), e.getLocalizedMessage()));
    }

    /**
     * NoSuchUserIdException에 대한 예외처리
     * 해당 user id가 존재하지 않을 경우에 발생
     * @param e
     * @return
     */
    @ExceptionHandler(NoSuchUserIdException.class)
    public ResponseEntity<Response<String>> handleNoSuchUserIdException(NoSuchUserIdException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Response.fail(HttpStatus.NOT_FOUND, e.getMessage(), e.getLocalizedMessage()));
    }

    /**
     * NoSuchPostIdException에 대한 예외처리
     * 해당 post id가 존재하지 않을 경우에 발생
     * @param e
     * @return
     */
    @ExceptionHandler(NoSuchPostIdException.class)
    public ResponseEntity<Response<String>> handleNoSuchPostIdException(NoSuchPostIdException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Response.fail(HttpStatus.NOT_FOUND, e.getMessage(), e.getLocalizedMessage()));
    }

    /**
     * 그 밖에 발생하는 모든 예외 처리
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Response<String>> handleException(Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Response.fail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e.getLocalizedMessage()));
    }
}
