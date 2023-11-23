package kdt.jpa.board.global.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BoardApiExceptionHandler {

    @ExceptionHandler(BoardException.class)
    private ResponseEntity<ErrorResponse> handleCustomException(BoardException e) {
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
    }
}
