package com.programmers.boardjpa.global.controller;

import com.programmers.boardjpa.post.exception.PostException;
import com.programmers.boardjpa.user.exception.UserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(PostException.class)
    public ResponseEntity<String> catchPostException(PostException e) {
        log.error(e.getErrorCode().getErrorMessage(), e);
        return ResponseEntity.status(e.getErrorCode().getErrorHttpStatus()).body(e.getErrorCode().getErrorMessage());
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<String> catchUserException(UserException e) {
        log.error(e.getErrorCode().getErrorMessage(), e);
        return ResponseEntity.status(e.getErrorCode().getErrorHttpStatus()).body(e.getErrorCode().getErrorMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> catchDefaultException(Exception e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("알 수 없는 에러가 발생했습니다!");
    }
}
