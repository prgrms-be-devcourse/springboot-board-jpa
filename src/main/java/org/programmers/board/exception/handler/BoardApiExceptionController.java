package org.programmers.board.exception.handler;

import org.programmers.board.exception.EmptyContentException;
import org.programmers.board.exception.EmptyTitleException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BoardApiExceptionController {

    @ExceptionHandler(EmptyContentException.class)
    public ResponseEntity<String> emptyContentExceptionHandle(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(EmptyTitleException.class)
    public ResponseEntity<String> emptyTitleExceptionHandle(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

}